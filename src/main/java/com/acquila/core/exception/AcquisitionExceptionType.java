package com.acquila.core.exception;

import com.acquila.common.validation.exception.AcquilaExceptionType;

import lombok.Getter;

/**
 * Enumerated type for acquisition exceptions.
 */
@Getter
public enum AcquisitionExceptionType implements AcquilaExceptionType {

    ACQUISITION_RETRIEVAL_FAILED(01, "Acquisition retrieval failed"),

    ACQUISITION_CREATION_FAILED(02, "Acquisition creation failed"),

    ACQUISITION_UPDATE_FAILED(03, "Acquisition update failed"),

    LIMIT_CHECKING_FAILED(04, "Limit checking failed.");

    private final int code;
    private final String message;

    AcquisitionExceptionType(final int code, final String message) {
        this.code = code;
        this.message = message;
    }
}
