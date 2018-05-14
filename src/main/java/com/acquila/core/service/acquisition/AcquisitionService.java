package com.acquila.core.service.acquisition;

import java.math.BigDecimal;

import com.acquila.common.dto.pagination.PaginationRequest;
import com.acquila.common.dto.pagination.PaginationResponse;
import com.acquila.common.dto.request.DirectAcquisitionDetails;
import com.acquila.common.dto.request.ProcedureDetails;
import com.acquila.common.dto.request.UpdateStatusDetails;
import com.acquila.common.dto.response.AcquisitionDetailsResponse;
import com.acquila.common.dto.response.CentralizedDetails;

/**
 * Operations related to acquisitions.
 */
public interface AcquisitionService {

    /**
     * Retrieves all the services, paginated.
     *
     *
     * @param year
     * @param paginationRequest - the provided pagination details.
     * @return - paginated acquisitions.
     */
    PaginationResponse<AcquisitionDetailsResponse> getAllServices(int year, PaginationRequest paginationRequest);

    /**
     * Retrieves all the works, paginated.
     *
     *
     * @param year
     * @param paginationRequest - the provided pagination details.
     * @return - paginated acquisitions.
     */
    PaginationResponse<AcquisitionDetailsResponse> getAllWorks(int year, PaginationRequest paginationRequest);

    /**
     * Retrieves all the procedures, paginated.
     *
     *
     * @param year
     * @param paginationRequest - the provided pagination details.
     * @return - paginated acquisitions.
     */
    PaginationResponse<AcquisitionDetailsResponse> getAllProcedures(int year, PaginationRequest paginationRequest);

    /**
     * Create a new direct acquisition.
     *
     * @param acquisitionDetails - the provided details of the new acquisition.
     */
    void createDirectAcquisition(DirectAcquisitionDetails acquisitionDetails);

    /**
     * Create a new procedure.
     *
     * @param acquisitionDetails - the provided details of the new procedure.
     */
    void createProcedure(ProcedureDetails acquisitionDetails);

    /**
     * Verifies if the provided amount, added to the total amount spent on acquisitions of the specified CPV code exceeds the limit.
     *
     * @param amount  - the provided amount for the current acquisition.
     * @param cpvCode - the CPV code of the acquisition.
     * @param type
     * @return - true if it exceeds, false if not.
     */
    boolean isOverLimit(BigDecimal amount, String cpvCode, String type);

    /**
     * Retrieve the centralized acquisition data.
     *
     * @param paginationRequest - the provided pagination details.
     * @param year              - the year for which we are retrieving the centralized acquisitions.
     * @return - paginated centralized acquisitions.
     */
    PaginationResponse<CentralizedDetails> getCentralizedAcquisitions(int year, PaginationRequest paginationRequest);

    /**
     * Update an acquisition's status.
     *
     * @param updateStatusDetails - the provided update acquisition details.
     */
    void updateAcquisitionStatus(UpdateStatusDetails updateStatusDetails);
}
