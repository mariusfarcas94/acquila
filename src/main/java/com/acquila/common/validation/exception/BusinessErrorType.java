package com.acquila.common.validation.exception;

/**
 * Common interface that will describe the business error types enums.
 * Needed to treat all the business errors in a consistent and generic way.
 */
public interface BusinessErrorType {

    int getCode();

    String getMessage();

}
