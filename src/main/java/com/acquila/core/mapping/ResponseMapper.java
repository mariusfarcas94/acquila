package com.acquila.core.mapping;

import com.acquila.common.dto.response.AcquisitionDetailsResponse;
import com.acquila.core.entity.DirectAcquisition;
import com.acquila.core.entity.Procedure;

/**
 * Mapper class for response objects.
 */
public class ResponseMapper {

    public static AcquisitionDetailsResponse mapToAcquisitionDetails(final DirectAcquisition directAcquisition) {
        return AcquisitionDetailsResponse.builder()
                .orderingNumber(directAcquisition.getOrderingNumber())
                .objective(directAcquisition.getObjective())
                .status(directAcquisition.getStatus().name())
                .cpvCode(directAcquisition.getCpvCode())
                .creator(directAcquisition.getCreator().getUsername())
                .estimatedValue(directAcquisition.getEstimatedValue())
                .financingSource(directAcquisition.getFinancingSource())
                .initialDate(directAcquisition.getInitialDate())
                .finalDate(directAcquisition.getFinalDate())
                .build();
    }

    public static AcquisitionDetailsResponse mapToAcquisitionDetails(final Procedure procedure) {
        return AcquisitionDetailsResponse.builder()
                .orderingNumber(procedure.getOrderingNumber())
                .objective(procedure.getObjective())
                .status(procedure.getStatus().name())
                .cpvCode(procedure.getCpvCode())
                .creator(procedure.getCreator().getUsername())
                .estimatedValue(procedure.getEstimatedValue())
                .financingSource(procedure.getFinancingSource())
                .initialDate(procedure.getEstimatedPeriod())
                .build();
    }
}
