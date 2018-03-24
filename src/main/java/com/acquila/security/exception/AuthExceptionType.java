package com.acquila.security.exception;


import com.acquila.core.validation.exception.AcquilaExceptionType;

import lombok.Getter;

/**
 * Describes types of auth exceptions.
 */
@Getter
public enum AuthExceptionType implements AcquilaExceptionType {

    TOKEN_GENERATION_FAILED(22001, "Could not generate tokens."),
    SESSION_NOT_FOUND_FOR_TOKEN(22002, "User session expired or token invalid."),
    PARSING_ACCOUNT_FROM_TOKEN_FAILED(22003, "Failed to extract data from valid token.");

    private final int code;

    private String message;

    AuthExceptionType(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
