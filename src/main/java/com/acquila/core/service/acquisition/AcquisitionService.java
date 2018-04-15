package com.acquila.core.service.acquisition;

import com.acquila.common.dto.pagination.PaginationRequest;
import com.acquila.common.dto.pagination.PaginationResponse;
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
}
