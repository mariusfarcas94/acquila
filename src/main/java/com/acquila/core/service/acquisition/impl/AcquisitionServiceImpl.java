package com.acquila.core.service.acquisition.impl;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.acquila.common.dto.pagination.PaginationRequest;
import com.acquila.common.dto.pagination.PaginationResponse;
import com.acquila.common.dto.request.DirectAcquisitionDetails;
import com.acquila.common.dto.response.AcquisitionDetails;
import com.acquila.core.entity.OrderNumber;
import com.acquila.core.entity.Service;
import com.acquila.core.entity.Work;
import com.acquila.core.enumerated.AcquisitionType;
import com.acquila.core.enumerated.DirectAcquisitionStatus;
import com.acquila.core.exception.AcquisitionExceptionProvider;
import com.acquila.core.mapping.ResponseMapper;
import com.acquila.core.repository.OrderNumberRepository;
import com.acquila.core.repository.ServiceRepository;
import com.acquila.core.repository.WorkRepository;
import com.acquila.core.service.acquisition.AcquisitionService;

import static java.time.LocalDate.now;
import static com.acquila.common.dto.pagination.mapper.PaginationMapper.buildPaginationResponseDto;
import static com.acquila.common.dto.pagination.mapper.PaginationMapper.toPageRequest;
import static com.acquila.common.validation.ObjectValidator.throwIfInvalid;
import static com.acquila.common.validation.exception.GenericBusinessErrorProvider.notFoundError;
import static com.acquila.core.exception.AcquisitionExceptionProvider.createAcquisitionException;
import static com.acquila.core.mapping.RequestMapper.buildServiceEntity;
import static com.acquila.core.mapping.RequestMapper.buildWorkEntity;

/**
 * {@inheritDoc}.
 */
@Component
public class AcquisitionServiceImpl implements AcquisitionService {

    private final ServiceRepository serviceRepository;

    private final WorkRepository workRepository;

//    private final ProcedureRepository procedureRepository;

    private final OrderNumberRepository orderNumberRepository;

    public AcquisitionServiceImpl(final ServiceRepository serviceRepository,
                                  final WorkRepository workRepository,
//                                  final ProcedureRepository procedureRepository,
                                  final OrderNumberRepository orderNumberRepository) {
        this.serviceRepository = serviceRepository;
        this.workRepository = workRepository;
//        this.procedureRepository = procedureRepository;
        this.orderNumberRepository = orderNumberRepository;
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    @Transactional(readOnly = true)
    public PaginationResponse<AcquisitionDetails> getAllServices(final PaginationRequest paginationRequest) {
        throwIfInvalid(paginationRequest, AcquisitionExceptionProvider::getAcquisitionsException);

        final PageRequest pageRequest = toPageRequest(paginationRequest);
        final Page<Service> page = serviceRepository.findAll(pageRequest);

        return buildPaginationResponseDto(page, ResponseMapper::mapToAcquisitionDetails);
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    @Transactional
    public boolean createDirectAcquisition(final DirectAcquisitionDetails acquisitionDetails) {
        throwIfInvalid(acquisitionDetails, AcquisitionExceptionProvider::createAcquisitionException);

        switch (acquisitionDetails.getType()) {
            case SERVICE:
                createService(acquisitionDetails);
                break;
            case WORK:
                createWork(acquisitionDetails);
                break;
            default:
                return false;
        }

        return true;
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

        serviceRepository.save(service);
    }

    /**
     * Create a new acquisition of the type work.
     *
     * @param acquisitionDetails - the new work details.
     */
    private void createWork(DirectAcquisitionDetails acquisitionDetails) {

        final Work work = buildWorkEntity(acquisitionDetails);

        work.setOrderingNumber(getNextOrderNumber(acquisitionDetails.getType()));
        work.setStatus(DirectAcquisitionStatus.PLANNED);

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
}
