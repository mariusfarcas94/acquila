package com.acquila.core.mapping;

import com.acquila.common.dto.request.AcquisitionDetails;
import com.acquila.core.entity.Procedure;
import com.acquila.core.entity.Service;
import com.acquila.core.entity.Work;

/**
 * Mapper class for request objects.
 */
public class RequestMapper {

    /**
     * Create a new Service entity, based on the provided AcquisitionDetails.
     *
     * @param acquisitionDetails - the provided acquisition details.
     * @return the new entity.
     */
    public static Service buildServiceEntity(final AcquisitionDetails acquisitionDetails) {
        final Service service = new Service();
        service.setObjective(acquisitionDetails.getObjective());
        service.setCpvCode(acquisitionDetails.getCpvCode());
        service.setEstimatedValue(acquisitionDetails.getEstimatedValue());
        service.setFinancingSource(acquisitionDetails.getFinancingSource());
        service.setInitialDate(acquisitionDetails.getInitialDate());
        service.setFinalDate(acquisitionDetails.getFinalDate());

        return service;
    }

    /**
     * Create a new Work entity, based on the provided AcquisitionDetails.
     *
     * @param acquisitionDetails - the provided acquisition details.
     * @return the new entity.
     */
    public static Work buildWorkEntity(final AcquisitionDetails acquisitionDetails) {
        final Work work = new Work();
        work.setObjective(acquisitionDetails.getObjective());
        work.setCpvCode(acquisitionDetails.getCpvCode());
        work.setEstimatedValue(acquisitionDetails.getEstimatedValue());
        work.setFinancingSource(acquisitionDetails.getFinancingSource());
        work.setInitialDate(acquisitionDetails.getInitialDate());
        work.setFinalDate(acquisitionDetails.getFinalDate());

        return work;
    }

    /**
     * Create a new Procedure entity, based on the provided AcquisitionDetails.
     *
     * @param acquisitionDetails - the provided acquisition details.
     * @return the new entity.
     */
    public static Procedure buildProcedureEntity(final AcquisitionDetails acquisitionDetails) {
        final Procedure procedure = new Procedure();
        procedure.setObjective(acquisitionDetails.getObjective());
        procedure.setCpvCode(acquisitionDetails.getCpvCode());
        procedure.setEstimatedValue(acquisitionDetails.getEstimatedValue());
        procedure.setFinancingSource(acquisitionDetails.getFinancingSource());
        procedure.setEstimatedPeriod(acquisitionDetails.getInitialDate());

        return procedure;
    }
}
