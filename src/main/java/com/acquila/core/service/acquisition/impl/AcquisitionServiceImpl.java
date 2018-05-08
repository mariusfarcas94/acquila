package com.acquila.core.service.acquisition.impl;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.acquila.common.dto.pagination.PaginationRequest;
import com.acquila.common.dto.pagination.PaginationResponse;
import com.acquila.common.dto.request.AcquisitionDetails;
import com.acquila.common.dto.response.AcquisitionDetailsResponse;
import com.acquila.core.entity.Account;
import com.acquila.core.entity.OrderNumber;
import com.acquila.core.entity.Procedure;
import com.acquila.core.entity.Service;
import com.acquila.core.entity.Work;
import com.acquila.core.enumerated.AcquisitionType;
import com.acquila.core.enumerated.DirectAcquisitionStatus;
import com.acquila.core.enumerated.Limit;
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
import static com.acquila.common.dto.pagination.mapper.PaginationMapper.buildPaginationResponseDto;
import static com.acquila.common.dto.pagination.mapper.PaginationMapper.toPageRequest;
import static com.acquila.common.validation.ObjectValidator.throwIfInvalid;
import static com.acquila.common.validation.exception.GenericBusinessErrorProvider.notFoundError;
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
    public PaginationResponse<AcquisitionDetailsResponse> getAllServices(final PaginationRequest paginationRequest) {
        throwIfInvalid(paginationRequest, AcquisitionExceptionProvider::getAcquisitionsException);

        final PageRequest pageRequest = toPageRequest(paginationRequest);
        final Page<Service> page = serviceRepository.findAll(pageRequest);

        return buildPaginationResponseDto(page, ResponseMapper::mapToAcquisitionDetails);
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    @Transactional(readOnly = true)
    public PaginationResponse<AcquisitionDetailsResponse> getAllWorks(PaginationRequest paginationRequest) {
        throwIfInvalid(paginationRequest, AcquisitionExceptionProvider::getAcquisitionsException);

        final PageRequest pageRequest = toPageRequest(paginationRequest);
        final Page<Work> page = workRepository.findAll(pageRequest);

        return buildPaginationResponseDto(page, ResponseMapper::mapToAcquisitionDetails);
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    @Transactional(readOnly = true)
    public PaginationResponse<AcquisitionDetailsResponse> getAllProcedures(PaginationRequest paginationRequest) {
        throwIfInvalid(paginationRequest, AcquisitionExceptionProvider::getAcquisitionsException);

        final PageRequest pageRequest = toPageRequest(paginationRequest);
        final Page<Procedure> page = procedureRepository.findAll(pageRequest);

        return buildPaginationResponseDto(page, ResponseMapper::mapToAcquisitionDetails);
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    @Transactional
    public void createDirectAcquisition(final AcquisitionDetails acquisitionDetails) {
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
    public void createProcedure(AcquisitionDetails acquisitionDetails) {
        final Procedure procedure = buildProcedureEntity(acquisitionDetails);

        procedure.setOrderingNumber(getNextOrderNumber(acquisitionDetails.getType()));
        procedure.setStatus(ProcedureStatus.PLANNED);
        procedure.setCreator(buildBobiAccount());
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    @Transactional(readOnly = true)
    public boolean isOverLimit(BigDecimal amount, String cpvCode, String type) {
        return acquisitionRepository.isOverLimit(amount, Limit.valueOf(type).getValue(), cpvCode);
    }

    /**
     * Create a new acquisition of the type service.
     *
     * @param acquisitionDetails - the new service details.
     */
    private void createService(final AcquisitionDetails acquisitionDetails) {

        final Service service = buildServiceEntity(acquisitionDetails);

        service.setOrderingNumber(getNextOrderNumber(acquisitionDetails.getType()));
        service.setStatus(DirectAcquisitionStatus.PLANNED);
        service.setCreator(buildBobiAccount());

        serviceRepository.save(service);
    }

    /**
     * Create a new acquisition of the type work.
     *
     * @param acquisitionDetails - the new work details.
     */
    private void createWork(AcquisitionDetails acquisitionDetails) {

        final Work work = buildWorkEntity(acquisitionDetails);

        work.setOrderingNumber(getNextOrderNumber(acquisitionDetails.getType()));
        work.setStatus(DirectAcquisitionStatus.PLANNED);
        work.setCreator(buildBobiAccount());

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
}
