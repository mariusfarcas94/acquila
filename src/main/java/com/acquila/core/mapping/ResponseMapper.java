package com.acquila.core.mapping;

import com.acquila.common.dto.response.AcquisitionDetails;
import com.acquila.core.entity.DirectAcquisition;

/**
 * Mapper class for response objects.
 */
public class ResponseMapper {

    public static AcquisitionDetails mapToAcquisitionDetails(final DirectAcquisition directAcquisition) {
        return AcquisitionDetails.builder()
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
}
