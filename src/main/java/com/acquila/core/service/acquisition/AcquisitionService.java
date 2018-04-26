package com.acquila.core.service.acquisition;

import java.math.BigDecimal;

import com.acquila.common.dto.pagination.PaginationRequest;
import com.acquila.common.dto.pagination.PaginationResponse;
import com.acquila.common.dto.request.AcquisitionDetails;
import com.acquila.common.dto.response.AcquisitionDetailsResponse;

/**
 * Operations related to acquisitions.
 */
public interface AcquisitionService {

    /**
     * Retrieves all the services, paginated.
     *
     * @param paginationRequest - the provided pagination details.
     * @return - paginated acquisitions.
     */
    PaginationResponse<AcquisitionDetailsResponse> getAllServices(PaginationRequest paginationRequest);

    /**
     * Retrieves all the works, paginated.
     *
     * @param paginationRequest - the provided pagination details.
     * @return - paginated acquisitions.
     */
    PaginationResponse<AcquisitionDetailsResponse> getAllWorks(PaginationRequest paginationRequest);

    /**
     * Retrieves all the procedures, paginated.
     *
     * @param paginationRequest - the provided pagination details.
     * @return - paginated acquisitions.
     */
    PaginationResponse<AcquisitionDetailsResponse> getAllProcedures(PaginationRequest paginationRequest);

    /**
     * Create a new direct acquisition.
     *
     * @param acquisitionDetails - the provided details of the new acquisition.
     */
    void createDirectAcquisition(AcquisitionDetails acquisitionDetails);

    /**
     * Create a new acquisition procedure.
     *
     * @param acquisitionDetails - the provided details of the new procedure.
     */
    void createProcedure(AcquisitionDetails acquisitionDetails);

    /**
     * Verifies if the provided amount, added to the total amount spent on acquisitions of the specified CPV code exceeds the limit.
     *
     * @param amount  - the provided amount for the current acquisition.
     * @param cpvCode - the CPV code of the acquisition.
     * @return - true if it exceeds, false if not.
     */
    boolean isOverLimit(BigDecimal amount, String cpvCode);
}
