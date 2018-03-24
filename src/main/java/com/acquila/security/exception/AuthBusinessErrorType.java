package com.acquila.security.exception;

import com.acquila.common.validation.exception.BusinessErrorType;

import lombok.Getter;

/**
 * Enum holding the error types and their codes.
 */
@Getter
public enum AuthBusinessErrorType implements BusinessErrorType {

    SESSION_EXPIRED(22501, "session_expired");


    private int code;
    private String message;

    AuthBusinessErrorType(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
