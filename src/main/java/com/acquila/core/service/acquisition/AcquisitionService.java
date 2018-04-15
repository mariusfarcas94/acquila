package com.acquila.core.service.acquisition;

import com.acquila.common.dto.pagination.PaginationRequest;
import com.acquila.common.dto.pagination.PaginationResponse;
import com.acquila.common.dto.request.DirectAcquisitionDetails;
import com.acquila.common.dto.response.AcquisitionDetails;

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
    PaginationResponse<AcquisitionDetails> getAllServices(final PaginationRequest paginationRequest);

    /**
     * Create a new direct acquisition.
     *
     * @param acquisitionDetails - the provided details of the new acquisition.
     * @return success status.
     */
    boolean createDirectAcquisition(DirectAcquisitionDetails acquisitionDetails);
}
