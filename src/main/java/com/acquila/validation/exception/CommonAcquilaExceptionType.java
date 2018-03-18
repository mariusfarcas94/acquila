package com.acquila.validation.exception;

/**
 * An enum holding the common exception type (illegal arguments exceptions, etc).
 */
public enum CommonAcquilaExceptionType implements AcquilaExceptionType {

    ILLEGAL_ARGUMENT_EXCEPTION(101000, "Illegal arguments exception.");

    private int code;

    private String message;

    CommonAcquilaExceptionType(int code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
