package com.acquila.core.mapping;

import com.acquila.common.dto.request.DirectAcquisitionDetails;
import com.acquila.common.dto.request.ProcedureDetails;
import com.acquila.core.entity.Procedure;
import com.acquila.core.entity.Service;
import com.acquila.core.entity.Work;

/**
 * Mapper class for request objects.
 */
public class RequestMapper {

    /**
     * Create a new Service entity, based on the provided DirectAcquisitionDetails.
     *
     * @param acquisitionDetails - the provided acquisition details.
     * @return the new entity.
     */
    public static Service buildServiceEntity(final DirectAcquisitionDetails acquisitionDetails) {
        final Service service = new Service();
        service.setObjective(acquisitionDetails.getObjective());
        service.setCpvCode(acquisitionDetails.getCpvCode());
        service.setEstimatedValue(acquisitionDetails.getEstimatedValue());
        service.setFinancingSource(acquisitionDetails.getFinancingSource());
        service.setInitialDate(acquisitionDetails.getInitialDate());
        service.setFinalDate(acquisitionDetails.getFinalDate());
        service.setYear(acquisitionDetails.getYear());

        return service;
    }

    /**
     * Create a new Work entity, based on the provided DirectAcquisitionDetails.
     *
     * @param acquisitionDetails - the provided acquisition details.
     * @return the new entity.
     */
    public static Work buildWorkEntity(final DirectAcquisitionDetails acquisitionDetails) {
        final Work work = new Work();
        work.setObjective(acquisitionDetails.getObjective());
        work.setCpvCode(acquisitionDetails.getCpvCode());
        work.setEstimatedValue(acquisitionDetails.getEstimatedValue());
        work.setFinancingSource(acquisitionDetails.getFinancingSource());
        work.setInitialDate(acquisitionDetails.getInitialDate());
        work.setFinalDate(acquisitionDetails.getFinalDate());
        work.setYear(acquisitionDetails.getYear());

        return work;
    }

    /**
     * Create a new Procedure entity, based on the provided DirectAcquisitionDetails.
     *
     * @param procedureDetails - the provided acquisition details.
     * @return the new entity.
     */
    public static Procedure buildProcedureEntity(final ProcedureDetails procedureDetails) {
        final Procedure procedure = new Procedure();
        procedure.setObjective(procedureDetails.getObjective());
        procedure.setCpvCode(procedureDetails.getCpvCode());
        procedure.setEstimatedValue(procedureDetails.getEstimatedValue());
        procedure.setFinancingSource(procedureDetails.getFinancingSource());
        procedure.setEstimatedPeriod(procedureDetails.getInitialDate());
        procedure.setProcedureType(procedureDetails.getProcedureType());

        return procedure;
    }
}
