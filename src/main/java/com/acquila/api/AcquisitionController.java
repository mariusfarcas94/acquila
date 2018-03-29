package com.acquila.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.acquila.common.dto.pagination.PaginationRequest;
import com.acquila.common.dto.pagination.PaginationResponse;
import com.acquila.common.dto.request.DirectAcquisitionDetails;
import com.acquila.common.dto.request.ProcedureDetails;
import com.acquila.common.dto.request.UpdateStatusDetails;
import com.acquila.common.dto.response.AcquisitionDetails;
import com.acquila.common.dto.response.CentralizedDetails;

import lombok.extern.log4j.Log4j2;

import static com.acquila.api.Constants.ACQUISITION;
import static com.acquila.api.Constants.ALL;
import static com.acquila.api.Constants.ARCHIVE;
import static com.acquila.api.Constants.CENTRALIZER;
import static com.acquila.api.Constants.CPV_CODE;
import static com.acquila.api.Constants.CREATE;
import static com.acquila.api.Constants.DETAILS;
import static com.acquila.api.Constants.DIRECT;
import static com.acquila.api.Constants.ID;
import static com.acquila.api.Constants.ID_VARIABLE;
import static com.acquila.api.Constants.PAGE_NUMBER;
import static com.acquila.api.Constants.PAGE_SIZE;
import static com.acquila.api.Constants.PROCEDURE;
import static com.acquila.api.Constants.SERVICE;
import static com.acquila.api.Constants.TYPE;
import static com.acquila.api.Constants.TYPE_VARIABLE;
import static com.acquila.api.Constants.UPDATE;
import static com.acquila.api.Constants.WORK;
import static com.acquila.common.dto.pagination.mapper.PaginationMapper.buildPaginationRequest;

/**
 * Used for random testing purposes.
 */
@RestController
@RequestMapping(path = ACQUISITION)
@Log4j2
public class AcquisitionController {

    private static final String ALL_SERVICES_PATH = DIRECT + SERVICE + ALL;

    private static final String ALL_WORKS_PATH = DIRECT + WORK + ALL;

    private static final String ALL_PROCEDURES_PATH = PROCEDURE + ALL;

    private static final String ALL_ARCHIVE_PATH = ARCHIVE + ALL;

    private static final String CENTRALIZER_PATH = CENTRALIZER + TYPE_VARIABLE;

    private static final String DETAILS_PATH = DETAILS + ID_VARIABLE;

    private static final String CREATE_DIRECT_ACQUISITION_PATH = DIRECT + CREATE;

    private static final String CREATE_PROCEDURE_PATH = PROCEDURE + CREATE;

    private static final String UPDATE_ACQUISITION_PATH = UPDATE;


    @GetMapping(ALL_SERVICES_PATH)
    public ResponseEntity<PaginationResponse<AcquisitionDetails>> getAllServices(@RequestParam(name = PAGE_NUMBER) final int pageNumber,
                                                                                 @RequestParam(name = PAGE_SIZE) final int pageSize) {
        final PaginationRequest paginationRequest = buildPaginationRequest(pageSize, pageNumber);

        log.debug("Pagination Request: ", paginationRequest);

        return new ResponseEntity<>(new PaginationResponse<>(), HttpStatus.OK);
    }

    @GetMapping(ALL_WORKS_PATH)
    public ResponseEntity<PaginationResponse<AcquisitionDetails>> getAllWorks(@RequestParam(name = PAGE_NUMBER) final int pageNumber,
                                                                              @RequestParam(name = PAGE_SIZE) final int pageSize) {
        final PaginationRequest paginationRequest = buildPaginationRequest(pageSize, pageNumber);
        log.debug("Pagination Request: ", paginationRequest);

        return new ResponseEntity<>(new PaginationResponse<>(), HttpStatus.OK);
    }

    @GetMapping(ALL_PROCEDURES_PATH)
    public ResponseEntity<PaginationResponse<AcquisitionDetails>> getAllProcedures(@RequestParam(name = PAGE_NUMBER) final int pageNumber,
                                                                                   @RequestParam(name = PAGE_SIZE) final int pageSize) {
        final PaginationRequest paginationRequest = buildPaginationRequest(pageSize, pageNumber);
        log.debug("Pagination Request: ", paginationRequest);

        return new ResponseEntity<>(new PaginationResponse<>(), HttpStatus.OK);
    }

    @GetMapping(ALL_ARCHIVE_PATH)
    public ResponseEntity<PaginationResponse<AcquisitionDetails>> getArchive(@RequestParam(name = PAGE_NUMBER) final int pageNumber,
                                                                             @RequestParam(name = PAGE_SIZE) final int pageSize) {
        final PaginationRequest paginationRequest = buildPaginationRequest(pageSize, pageNumber);
        log.debug("Pagination Request: ", paginationRequest);

        return new ResponseEntity<>(new PaginationResponse<>(), HttpStatus.OK);
    }

    @GetMapping(CENTRALIZER_PATH)
    public ResponseEntity<CentralizedDetails> getCentralizer(@PathVariable(name = TYPE) final String type,
                                                             @RequestParam(name = CPV_CODE) final int cpvCode) {
        log.debug("Type: " + type, ", CPV Code: " + cpvCode);

        return new ResponseEntity<>(new CentralizedDetails(), HttpStatus.OK);
    }

    @GetMapping(DETAILS_PATH)
    public ResponseEntity<AcquisitionDetails> getAcquisitionDetails(@RequestParam(name = ID) final String id) {
        log.debug("Acquisition ID: " + id);

        return new ResponseEntity<>(new AcquisitionDetails(), HttpStatus.OK);
    }

    @PostMapping(CREATE_DIRECT_ACQUISITION_PATH)
    public ResponseEntity createDirectAcquisition(@RequestBody final DirectAcquisitionDetails acquisitionDetails) {
        log.debug("ServiceDetails: " + acquisitionDetails);

        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping(CREATE_PROCEDURE_PATH)
    public ResponseEntity createProcedure(@RequestBody final ProcedureDetails acquisitionDetails) {
        log.debug("ServiceDetails: " + acquisitionDetails);

        return new ResponseEntity(HttpStatus.OK);
    }

    @PutMapping(UPDATE_ACQUISITION_PATH)
    public ResponseEntity updateAcquisition(@RequestBody final UpdateStatusDetails updateStatusDetails) {
        log.debug("UpdateStatusDetails: " + updateStatusDetails);

        return new ResponseEntity(HttpStatus.OK);
    }
}
