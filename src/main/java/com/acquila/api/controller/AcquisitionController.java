package com.acquila.api.controller;

import java.math.BigDecimal;

import javax.servlet.http.HttpServletResponse;

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
import com.acquila.common.dto.request.AcquisitionDetails;
import com.acquila.common.dto.request.UpdateStatusDetails;
import com.acquila.common.dto.response.AcquisitionDetailsResponse;
import com.acquila.common.dto.response.CentralizedDetails;
import com.acquila.core.service.acquisition.AcquisitionService;

import lombok.extern.log4j.Log4j2;

import static com.acquila.api.controller.Constants.ACQUISITION;
import static com.acquila.api.controller.Constants.ALL;
import static com.acquila.api.controller.Constants.AMOUNT;
import static com.acquila.api.controller.Constants.ARCHIVE;
import static com.acquila.api.controller.Constants.CENTRALIZER;
import static com.acquila.api.controller.Constants.CPV_CODE;
import static com.acquila.api.controller.Constants.CREATE;
import static com.acquila.api.controller.Constants.DETAILS;
import static com.acquila.api.controller.Constants.DIRECT;
import static com.acquila.api.controller.Constants.ID;
import static com.acquila.api.controller.Constants.LIMIT;
import static com.acquila.api.controller.Constants.PAGE_NUMBER;
import static com.acquila.api.controller.Constants.PAGE_SIZE;
import static com.acquila.api.controller.Constants.PROCEDURE;
import static com.acquila.api.controller.Constants.SERVICE;
import static com.acquila.api.controller.Constants.TYPE;
import static com.acquila.api.controller.Constants.TYPE_VARIABLE;
import static com.acquila.api.controller.Constants.UPDATE;
import static com.acquila.api.controller.Constants.WORK;
import static com.acquila.common.dto.pagination.mapper.PaginationMapper.buildPaginationRequest;

/**
 * Used for random testing purposes.
 */
@RestController
@RequestMapping(path = ACQUISITION)
@Log4j2
public class AcquisitionController {

    //todo(mfarcas) - add aspect to treat exceptions uniformly

    private static final String ALL_SERVICES_PATH = DIRECT + SERVICE + ALL;

    private static final String ALL_WORKS_PATH = DIRECT + WORK + ALL;

    private static final String ALL_PROCEDURES_PATH = PROCEDURE + ALL;

    private static final String ALL_ARCHIVE_PATH = ARCHIVE + ALL;

    private static final String CENTRALIZER_PATH = CENTRALIZER + TYPE_VARIABLE;

    private static final String OVER_LIMIT_PATH = LIMIT;

    private static final String DETAILS_PATH = DETAILS;

    private static final String CREATE_DIRECT_ACQUISITION_PATH = DIRECT + CREATE;

    private static final String CREATE_PROCEDURE_PATH = PROCEDURE + CREATE;

    private static final String UPDATE_ACQUISITION_PATH = UPDATE;

    static {
        printControllerDetails();
    }

    private final AcquisitionService acquisitionService;

    public AcquisitionController(final AcquisitionService acquisitionService) {
        this.acquisitionService = acquisitionService;
    }

    @GetMapping(ALL_SERVICES_PATH)
    public ResponseEntity getAllServices(final HttpServletResponse response,
                                         @RequestParam(name = PAGE_NUMBER) final int pageNumber,
                                         @RequestParam(name = PAGE_SIZE) final int pageSize) {
        final PaginationRequest paginationRequest = buildPaginationRequest(pageSize, pageNumber);
        log.debug("Pagination Request: {}" + paginationRequest);

        final PaginationResponse<AcquisitionDetailsResponse> services = acquisitionService.getAllServices(paginationRequest);
        return new ResponseEntity<>(services, HttpStatus.OK);
    }

    @GetMapping(ALL_WORKS_PATH)
    public ResponseEntity<PaginationResponse<AcquisitionDetailsResponse>> getAllWorks(final HttpServletResponse response,
                                                                                      @RequestParam(name = PAGE_NUMBER) final int pageNumber,
                                                                                      @RequestParam(name = PAGE_SIZE) final int pageSize) {
        final PaginationRequest paginationRequest = buildPaginationRequest(pageSize, pageNumber);
        log.debug("Pagination Request: {}", paginationRequest);

        final PaginationResponse<AcquisitionDetailsResponse> services = acquisitionService.getAllWorks(paginationRequest);
        return new ResponseEntity<>(services, HttpStatus.OK);
    }

    @GetMapping(ALL_PROCEDURES_PATH)
    public ResponseEntity<PaginationResponse<AcquisitionDetailsResponse>> getAllProcedures(final HttpServletResponse response,
                                                                                           @RequestParam(name = PAGE_NUMBER) final int pageNumber,
                                                                                           @RequestParam(name = PAGE_SIZE) final int pageSize) {
        final PaginationRequest paginationRequest = buildPaginationRequest(pageSize, pageNumber);
        log.debug("Pagination Request: {}", paginationRequest);

        final PaginationResponse<AcquisitionDetailsResponse> services = acquisitionService.getAllProcedures(paginationRequest);
        return new ResponseEntity<>(services, HttpStatus.OK);
    }

    //todo(mfarcas) - implement method
    @GetMapping(ALL_ARCHIVE_PATH)
    public ResponseEntity<PaginationResponse<AcquisitionDetailsResponse>> getArchive(final HttpServletResponse response,
                                                                                     @RequestParam(name = PAGE_NUMBER) final int pageNumber,
                                                                                     @RequestParam(name = PAGE_SIZE) final int pageSize) {
        final PaginationRequest paginationRequest = buildPaginationRequest(pageSize, pageNumber);
        log.debug("Pagination Request: {}", paginationRequest);

        return new ResponseEntity<>(new PaginationResponse<>(), HttpStatus.OK);
    }

    //todo(mfarcas) - implement method
    @GetMapping(CENTRALIZER_PATH)
    public ResponseEntity<CentralizedDetails> getCentralizer(final HttpServletResponse response,
                                                             @PathVariable(name = TYPE) final String type,
                                                             @RequestParam(name = CPV_CODE) final int cpvCode) {
        log.debug("Type: " + type, ", CPV Code: " + cpvCode);

        return new ResponseEntity<>(new CentralizedDetails(), HttpStatus.OK);
    }

    @GetMapping(OVER_LIMIT_PATH)
    public ResponseEntity<Boolean> isOverLimit(final HttpServletResponse response,
                                               @RequestParam(name = AMOUNT) final BigDecimal amount,
                                               @RequestParam(name = CPV_CODE) final String cpvCode) {
        log.debug("CPV Code: " + cpvCode);

        return new ResponseEntity<>(acquisitionService.isOverLimit(amount, cpvCode),
                HttpStatus.OK);
    }

    @GetMapping(DETAILS_PATH)
    public ResponseEntity<AcquisitionDetailsResponse> getAcquisitionDetails(final HttpServletResponse response,
                                                                            @RequestParam(name = ID) final String id) {
        log.debug("Acquisition ID: " + id);

        return new ResponseEntity<>(new AcquisitionDetailsResponse(), HttpStatus.OK);
    }

    @PostMapping(CREATE_DIRECT_ACQUISITION_PATH)
    public ResponseEntity createDirectAcquisition(final HttpServletResponse response,
                                                  @RequestBody final AcquisitionDetails acquisitionDetails) {
        log.debug("AcquisitionDetails: " + acquisitionDetails);

        acquisitionService.createDirectAcquisition(acquisitionDetails);
        return new ResponseEntity<>(HttpStatus.OK);

    }

    @PostMapping(CREATE_PROCEDURE_PATH)
    public ResponseEntity createProcedure(final HttpServletResponse response,
                                          @RequestBody final AcquisitionDetails acquisitionDetails) {
        log.debug("ProcedureDetails: " + acquisitionDetails);

        acquisitionService.createProcedure(acquisitionDetails);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //todo(mfarcas) - implement method
    @PutMapping(UPDATE_ACQUISITION_PATH)
    public ResponseEntity updateAcquisition(@RequestBody final UpdateStatusDetails updateStatusDetails) {
        log.debug("UpdateStatusDetails: " + updateStatusDetails);

        return new ResponseEntity(HttpStatus.OK);
    }

    private static void printControllerDetails() {
        System.out.println("###### Acquisition Controller REST Endpoints ######");

        System.out.println("HTTP GET: " + ACQUISITION + ALL_SERVICES_PATH + " \n--Params: " + PAGE_NUMBER + ", " + PAGE_SIZE + "\n");
        System.out.println("HTTP GET: " + ACQUISITION + ALL_WORKS_PATH + " \n--Params: " + PAGE_NUMBER + ", " + PAGE_SIZE + "\n");
        System.out.println("HTTP GET: " + ACQUISITION + ALL_PROCEDURES_PATH + " \n--Params: " + PAGE_NUMBER + ", " + PAGE_SIZE + "\n");
        System.out.println("HTTP GET: " + ACQUISITION + ALL_ARCHIVE_PATH + " \n--Params: " + PAGE_NUMBER + ", " + PAGE_SIZE + "\n");
        System.out.println("HTTP GET: " + ACQUISITION + CENTRALIZER_PATH + " \n--Params: " + CPV_CODE + "\n");
        System.out.println("HTTP GET: " + ACQUISITION + DETAILS_PATH + " \n--Params: " + ID + "\n");
        System.out.println("HTTP POST: " + ACQUISITION + CREATE_DIRECT_ACQUISITION_PATH + "\n");
        System.out.println("HTTP POST: " + ACQUISITION + CREATE_PROCEDURE_PATH + "\n");
        System.out.println("HTTP POST: " + ACQUISITION + UPDATE_ACQUISITION_PATH + "\n");
    }
}
