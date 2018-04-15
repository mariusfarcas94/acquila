package com.acquila.core.mapping;

import com.acquila.common.dto.request.DirectAcquisitionDetails;
import com.acquila.core.entity.Service;
import com.acquila.core.entity.Work;

/**
 * Mapper class for request objects.
 */
public class RequestMapper {

    public static Service buildServiceEntity(final DirectAcquisitionDetails acquisitionDetails) {
        final Service service = new Service();
        service.setObjective(acquisitionDetails.getObjective());
        service.setCpvCode(acquisitionDetails.getCpvCode());
        service.setEstimatedValue(acquisitionDetails.getEstimatedValue());
        service.setFinancingSource(acquisitionDetails.getFinancingSource());
        service.setInitialDate(acquisitionDetails.getInitialDate());
        service.setFinalDate(acquisitionDetails.getFinalDate());

        return service;
    }

    public static Work buildWorkEntity(final DirectAcquisitionDetails acquisitionDetails) {
        final Work work = new Work();
        work.setObjective(acquisitionDetails.getObjective());
        work.setCpvCode(acquisitionDetails.getCpvCode());
        work.setEstimatedValue(acquisitionDetails.getEstimatedValue());
        work.setFinancingSource(acquisitionDetails.getFinancingSource());
        work.setInitialDate(acquisitionDetails.getInitialDate());
        work.setFinalDate(acquisitionDetails.getFinalDate());

        return work;
    }
}
