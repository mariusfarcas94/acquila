package com.acquila.api;

/**
 * Contains all the common parameters as constants.
 */
class Constants {

    // Controller paths
    static final String ACQUISITION = "/acquisition";

    // Common api paths
    static final String DIRECT = "/direct";
    static final String ALL = "/all";
    static final String SERVICE = "/service";
    static final String WORK = "/work";
    static final String PROCEDURE = "/procedure";
    static final String ARCHIVE = "/archive";
    static final String CENTRALIZER = "/centralizer";
    static final String DETAILS = "/details";
    static final String CREATE = "/create";
    static final String UPDATE = "/update";

    // Common api path variables
    static final String TYPE_VARIABLE = "/{type}";
    static final String ID_VARIABLE = "/{id}";

    // Common parameters names
    static final String PAGE_NUMBER = "pageNumber";
    static final String PAGE_SIZE = "pageSize";
    static final String CPV_CODE = "cpv";
    static final String ID = "id";
    static final String TYPE = "type";
}
