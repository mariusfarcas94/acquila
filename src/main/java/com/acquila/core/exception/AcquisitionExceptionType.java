package com.acquila.core.exception;

import com.acquila.common.validation.exception.AcquilaExceptionType;

import lombok.Getter;

/**
 * Enumerated type for acquisition exceptions.
 */
@Getter
public enum AcquisitionExceptionType implements AcquilaExceptionType {

    ACQUISITION_RETRIEVAL_FAILED(001, "Acquisition retrieval failed"),

    ACQUISITION_CREATION_FAILED(002, "Acquisition creation failed");

    private final int code;
    private final String message;

    AcquisitionExceptionType(final int code, final String message) {
        this.code = code;
        this.message = message;
    }
}
