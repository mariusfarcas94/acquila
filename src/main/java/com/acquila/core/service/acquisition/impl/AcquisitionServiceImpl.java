package com.acquila.core.service.acquisition.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import com.acquila.common.dto.pagination.PaginationRequest;
import com.acquila.common.dto.pagination.PaginationResponse;
import com.acquila.common.dto.response.AcquisitionDetails;
import com.acquila.core.entity.Service;
import com.acquila.core.exception.AcquisitionExceptionProvider;
import com.acquila.core.mapping.ResponseMapper;
import com.acquila.core.repository.ProcedureRepository;
import com.acquila.core.repository.ServiceRepository;
import com.acquila.core.repository.WorkRepository;
import com.acquila.core.service.acquisition.AcquisitionService;

import static com.acquila.common.dto.pagination.mapper.PaginationMapper.buildPaginationResponseDto;
import static com.acquila.common.dto.pagination.mapper.PaginationMapper.toPageRequest;
import static com.acquila.common.validation.ObjectValidator.throwIfInvalid;
import static com.acquila.common.validation.ObjectValidator.throwIfNull;
import static com.acquila.common.validation.exception.GenericBusinessErrorProvider.nullParameterError;
import static com.acquila.core.exception.AcquisitionExceptionProvider.getAcquisitionsException;

/**
 * {@inheritDoc}.
 */
@Component
public class AcquisitionServiceImpl implements AcquisitionService {

    private final ServiceRepository serviceRepository;

    private final WorkRepository workRepository;

    private final ProcedureRepository procedureRepository;

    public AcquisitionServiceImpl(final ServiceRepository serviceRepository,
                                  final WorkRepository workRepository,
                                  final ProcedureRepository procedureRepository) {
        this.serviceRepository = serviceRepository;
        this.workRepository = workRepository;
        this.procedureRepository = procedureRepository;
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public PaginationResponse<AcquisitionDetails> getAllServices(final PaginationRequest paginationRequest) {
        throwIfInvalid(paginationRequest, AcquisitionExceptionProvider::getAcquisitionsException);

        final PageRequest pageRequest = toPageRequest(paginationRequest);
        final Page<Service> page = serviceRepository.findAll(pageRequest);

        return buildPaginationResponseDto(page, ResponseMapper::mapToAcquisitionDetails);
    }
}
