package com.acquila.common.validation.exception;

/**
 * Common interface that will describe the exception types enums.
 * Needed to treat all the exceptions in a consistent and generic way.
 */
public interface AcquilaExceptionType {

    int getCode();

    String getMessage();
}
