package com.acquila.core.service.acquisition.impl;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.acquila.common.dto.pagination.PaginationRequest;
import com.acquila.common.dto.pagination.PaginationResponse;
import com.acquila.common.dto.request.DirectAcquisitionDetails;
import com.acquila.common.dto.request.ProcedureDetails;
import com.acquila.common.dto.request.UpdateStatusDetails;
import com.acquila.common.dto.response.AcquisitionDetailsResponse;
import com.acquila.common.dto.response.CentralizedDetails;
import com.acquila.core.entity.Account;
import com.acquila.core.entity.OrderNumber;
import com.acquila.core.entity.Procedure;
import com.acquila.core.entity.Service;
import com.acquila.core.entity.Work;
import com.acquila.core.enumerated.AcquisitionType;
import com.acquila.core.enumerated.DirectAcquisitionStatus;
import com.acquila.core.enumerated.ProcedureStatus;
import com.acquila.core.exception.AcquisitionExceptionProvider;
import com.acquila.core.mapping.ResponseMapper;
import com.acquila.core.repository.AcquisitionRepository;
import com.acquila.core.repository.OrderNumberRepository;
import com.acquila.core.repository.ProcedureRepository;
import com.acquila.core.repository.ServiceRepository;
import com.acquila.core.repository.WorkRepository;
import com.acquila.core.service.acquisition.AcquisitionService;

import static java.time.LocalDate.now;
import static java.util.Collections.singletonList;
import static java.util.Optional.ofNullable;
import static com.acquila.common.dto.pagination.mapper.PaginationMapper.buildPaginationResponseDto;
import static com.acquila.common.dto.pagination.mapper.PaginationMapper.toPageRequest;
import static com.acquila.common.dto.pagination.mapper.PaginationMapper.toSortedPageRequest;
import static com.acquila.common.validation.ObjectValidator.throwIfEmpty;
import static com.acquila.common.validation.ObjectValidator.throwIfInvalid;
import static com.acquila.common.validation.ObjectValidator.throwIfNull;
import static com.acquila.common.validation.exception.AcquilaExceptionProvider.illegalArgumentsException;
import static com.acquila.common.validation.exception.GenericBusinessErrorProvider.CPV_CODE_FIELD;
import static com.acquila.common.validation.exception.GenericBusinessErrorProvider.TYPE_FIELD;
import static com.acquila.common.validation.exception.GenericBusinessErrorProvider.emptyStringError;
import static com.acquila.common.validation.exception.GenericBusinessErrorProvider.invalidUUID;
import static com.acquila.common.validation.exception.GenericBusinessErrorProvider.notFoundError;
import static com.acquila.common.validation.exception.GenericBusinessErrorProvider.nullParameterError;
import static com.acquila.core.exception.AcquisitionExceptionProvider.checkLimitException;
import static com.acquila.core.exception.AcquisitionExceptionProvider.createAcquisitionException;
import static com.acquila.core.mapping.RequestMapper.buildProcedureEntity;
import static com.acquila.core.mapping.RequestMapper.buildServiceEntity;
import static com.acquila.core.mapping.RequestMapper.buildWorkEntity;

/**
 * {@inheritDoc}.
 */
@Component
public class AcquisitionServiceImpl implements AcquisitionService {

    private final ServiceRepository serviceRepository;

    private final WorkRepository workRepository;

    private final ProcedureRepository procedureRepository;

    private final OrderNumberRepository orderNumberRepository;

    private final AcquisitionRepository acquisitionRepository;

    public AcquisitionServiceImpl(final ServiceRepository serviceRepository,
                                  final WorkRepository workRepository,
                                  final ProcedureRepository procedureRepository,
                                  final OrderNumberRepository orderNumberRepository,
                                  final AcquisitionRepository acquisitionRepository) {
        this.serviceRepository = serviceRepository;
        this.workRepository = workRepository;
        this.procedureRepository = procedureRepository;
        this.orderNumberRepository = orderNumberRepository;
        this.acquisitionRepository = acquisitionRepository;
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    @Transactional(readOnly = true)
    public PaginationResponse<AcquisitionDetailsResponse> getAllServices(int year, final PaginationRequest paginationRequest) {
        throwIfInvalid(paginationRequest, AcquisitionExceptionProvider::getAcquisitionsException);

        final PageRequest pageRequest = toSortedPageRequest(paginationRequest);
        final Page<Service> page = serviceRepository.findAll(year, pageRequest);

        return buildPaginationResponseDto(page, ResponseMapper::mapToAcquisitionDetails);
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    @Transactional(readOnly = true)
    public PaginationResponse<AcquisitionDetailsResponse> getAllWorks(int year, final PaginationRequest paginationRequest) {
        throwIfInvalid(paginationRequest, AcquisitionExceptionProvider::getAcquisitionsException);

        final PageRequest pageRequest = toSortedPageRequest(paginationRequest);
        final Page<Work> page = workRepository.findAll(year, pageRequest);

        return buildPaginationResponseDto(page, ResponseMapper::mapToAcquisitionDetails);
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    @Transactional(readOnly = true)
    public PaginationResponse<AcquisitionDetailsResponse> getAllProcedures(int year, final PaginationRequest paginationRequest) {
        throwIfInvalid(paginationRequest, AcquisitionExceptionProvider::getAcquisitionsException);

        final PageRequest pageRequest = toSortedPageRequest(paginationRequest);
        final Page<Procedure> page = procedureRepository.findAll(year, pageRequest);

        return buildPaginationResponseDto(page, ResponseMapper::mapToAcquisitionDetails);
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    @Transactional
    public void createDirectAcquisition(final DirectAcquisitionDetails acquisitionDetails) {
        throwIfInvalid(acquisitionDetails, AcquisitionExceptionProvider::createAcquisitionException);

        switch (acquisitionDetails.getType()) {
            case SERVICE:
                createService(acquisitionDetails);
                break;
            case WORK:
                createWork(acquisitionDetails);
                break;
            default:
                return;
        }
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    @Transactional
    public void createProcedure(final ProcedureDetails procedureDetails) {
        throwIfInvalid(procedureDetails, AcquisitionExceptionProvider::createAcquisitionException);

        final Procedure procedure = buildProcedureEntity(procedureDetails);
        procedure.setOrderingNumber(getNextOrderNumber(procedureDetails.getType()));
        procedure.setStatus(ProcedureStatus.PLANNED);
        procedure.setCreator(buildBobiAccount());
        procedure.setType(AcquisitionType.PROCEDURE);

        procedureRepository.save(procedure);
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    @Transactional(readOnly = true)
    public boolean isOverLimit(final BigDecimal amount, final String cpvCode, final String type) {
        throwIfNull(amount, () -> checkLimitException(nullParameterError()));
        throwIfEmpty(cpvCode, () -> checkLimitException(emptyStringError(CPV_CODE_FIELD)));
        throwIfEmpty(type, () -> checkLimitException(emptyStringError(TYPE_FIELD)));

        final BigDecimal limit = AcquisitionType.valueOf(type).getValue();

        return acquisitionRepository.isOverLimit(amount, limit, cpvCode)
                .orElse(amount.compareTo(limit) > 0);
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    @Transactional(readOnly = true)
    public PaginationResponse<CentralizedDetails> getCentralizedAcquisitions(final int year, final PaginationRequest paginationRequest) {
        throwIfInvalid(paginationRequest, AcquisitionExceptionProvider::getAcquisitionsException);

        final PageRequest pageRequest = toPageRequest(paginationRequest);

        final OffsetDateTime from = getStartOfYear(year);
        final OffsetDateTime to = getEndOfYear(year);

        final Page<CentralizedDetails> page = acquisitionRepository.getCentralizedData(from, to, pageRequest);

        page.getContent().forEach(e -> decorateWithYearAndLimit(year, e));

        return buildPaginationResponseDto(page, Function.identity());
    }

    @Override
    @Transactional
    public void updateAcquisitionStatus(final UpdateStatusDetails updateStatusDetails) {
        throwIfInvalid(updateStatusDetails, AcquisitionExceptionProvider::updateAcquisitionException);

        final AcquisitionType type = AcquisitionType.valueOf(updateStatusDetails.getType());

        final String newStatus = updateStatusDetails.getNewStatus();

        switch (type) {
            case PROCEDURE:
                ofNullable(procedureRepository.findOne(uuidFromString(updateStatusDetails.getAcquisitionId())))
                        .ifPresent(p -> p.setStatus(ProcedureStatus.valueOf(newStatus)));
                break;
            case SERVICE:
                ofNullable(serviceRepository.findOne(uuidFromString(updateStatusDetails.getAcquisitionId())))
                        .ifPresent(p -> p.setStatus(DirectAcquisitionStatus.valueOf(newStatus)));
                break;
            case WORK:
                ofNullable(workRepository.findOne(uuidFromString(updateStatusDetails.getAcquisitionId())))
                        .ifPresent(p -> p.setStatus(DirectAcquisitionStatus.valueOf(newStatus)));
                break;
            default:
                break;
        }
    }

    /**
     * Decorate a centralized details object with the year and the limit corresponding to it's type.
     */
    private void decorateWithYearAndLimit(int year, CentralizedDetails centralizedDetails) {
        centralizedDetails.setYear(year);
        centralizedDetails.setLimit(centralizedDetails.getType().getValue());
    }

    /**
     * Compute the end of the current year.
     */
    private OffsetDateTime getEndOfYear(int year) {
        return OffsetDateTime.of(year, 12, 31, 23, 59, 59, 99999, ZoneOffset.UTC);
    }

    /**
     * Compute the start of the current year.
     */
    private OffsetDateTime getStartOfYear(int year) {
        return OffsetDateTime.of(year, 1, 1, 0, 0, 0, 1, ZoneOffset.UTC);
    }

    /**
     * Create a new acquisition of the type service.
     *
     * @param acquisitionDetails - the new service details.
     */
    private void createService(final DirectAcquisitionDetails acquisitionDetails) {

        final Service service = buildServiceEntity(acquisitionDetails);
        service.setOrderingNumber(getNextOrderNumber(acquisitionDetails.getType()));
        service.setStatus(DirectAcquisitionStatus.PLANNED);
        service.setCreator(buildBobiAccount());
        service.setType(AcquisitionType.SERVICE);

        serviceRepository.save(service);
    }

    /**
     * Create a new acquisition of the type work.
     *
     * @param acquisitionDetails - the new work details.
     */
    private void createWork(final DirectAcquisitionDetails acquisitionDetails) {

        final Work work = buildWorkEntity(acquisitionDetails);
        work.setOrderingNumber(getNextOrderNumber(acquisitionDetails.getType()));
        work.setStatus(DirectAcquisitionStatus.PLANNED);
        work.setCreator(buildBobiAccount());
        work.setType(AcquisitionType.WORK);

        workRepository.save(work);
    }

    /**
     * Retrieves the next ordering number for the specified acquisition type.
     * After the ordering number is retrieved, it is automatically incremented in the database.
     *
     * @param acquisitionType - the specified acquisition type.
     * @return the next order number.
     */
    private int getNextOrderNumber(final AcquisitionType acquisitionType) {
        final Optional<OrderNumber> orderNumber = orderNumberRepository.findByYear(now().getYear());

        switch (acquisitionType) {
            case SERVICE:
                return orderNumber.map(OrderNumber::getNextServiceNumber)
                        .orElseThrow(() -> createAcquisitionException(notFoundError("orderNumber")));
            case WORK:
                return orderNumber.map(OrderNumber::getNextWorkNumber)
                        .orElseThrow(() -> createAcquisitionException(notFoundError("orderNumber")));
            case PROCEDURE:
                return orderNumber.map(OrderNumber::getNextProcedureNumber)
                        .orElseThrow(() -> createAcquisitionException(notFoundError("orderNumber")));
            default:
                return -1;
        }
    }

    //todo(mfarcas) - remove this after implementing login.
    private Account buildBobiAccount() {
        final Account account = new Account();
        account.setId(UUID.fromString("018b36dd-cc81-4309-9875-ef1a7d47e6ac"));

        return account;
    }

    private UUID uuidFromString(String uuidString) {
        try {
            return UUID.fromString(uuidString);
        } catch (IllegalArgumentException e) {
            throw illegalArgumentsException(singletonList(invalidUUID("id")));
        }
    }

}
