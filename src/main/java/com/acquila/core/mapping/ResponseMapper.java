package com.acquila.core.mapping;

import java.time.format.DateTimeFormatter;

import com.acquila.common.dto.response.AcquisitionDetailsResponse;
import com.acquila.common.dto.response.CommentDetails;
import com.acquila.core.entity.Comment;
import com.acquila.core.entity.DirectAcquisition;
import com.acquila.core.entity.Procedure;

/**
 * Mapper class for response objects.
 */
public class ResponseMapper {

    public static AcquisitionDetailsResponse mapToAcquisitionDetails(final DirectAcquisition directAcquisition) {
        return AcquisitionDetailsResponse.builder()
                .id(directAcquisition.getId().toString())
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
                .id(procedure.getId().toString())
                .orderingNumber(procedure.getOrderingNumber())
                .objective(procedure.getObjective())
                .status(procedure.getStatus().name())
                .cpvCode(procedure.getCpvCode())
                .creator(procedure.getCreator().getUsername())
                .estimatedValue(procedure.getEstimatedValue())
                .financingSource(procedure.getFinancingSource())
                .initialDate(procedure.getEstimatedPeriod())
                .procedureType(procedure.getProcedureType())
                .build();
    }

    public static CommentDetails mapToCommentDetails(final Comment comment) {
        return CommentDetails.builder()
                .author(comment.getAccount().getUsername())
                .type(comment.getType().name())
                .text(comment.getText())
                .date(comment.getCreated().format(DateTimeFormatter.ISO_DATE_TIME))
                .build();
    }
}
