package com.acquila.common.validation.exception;


import static com.acquila.common.validation.exception.GenericBusinessErrorType.BLANK_STRING;
import static com.acquila.common.validation.exception.GenericBusinessErrorType.EMPTY_COLLECTION;
import static com.acquila.common.validation.exception.GenericBusinessErrorType.EMPTY_STRING;
import static com.acquila.common.validation.exception.GenericBusinessErrorType.INPUT_TOO_SHORT;
import static com.acquila.common.validation.exception.GenericBusinessErrorType.INTERNAL_SERVER_ERROR;
import static com.acquila.common.validation.exception.GenericBusinessErrorType.INVALID_EMAIL_FORMAT;
import static com.acquila.common.validation.exception.GenericBusinessErrorType.INVALID_UUID_FORMAT;
import static com.acquila.common.validation.exception.GenericBusinessErrorType.MIN_VALUE;
import static com.acquila.common.validation.exception.GenericBusinessErrorType.NOT_FOUND;
import static com.acquila.common.validation.exception.GenericBusinessErrorType.NULL_FIELD;
import static com.acquila.common.validation.exception.GenericBusinessErrorType.NULL_PARAMETER;

/**
 * Factory class used to hide the creation logic of BusinessError objects.
 */
public class GenericBusinessErrorProvider {

    private static final String EMAIL_FIELD = "email";

    public static BusinessError nullParameterError() {
        return new BusinessError(NULL_PARAMETER);
    }

    public static BusinessError invalidUUID(final String field) {
        return new BusinessError(INVALID_UUID_FORMAT, field);
    }

    public static BusinessError emptyStringError(final String field) {
        return new BusinessError(EMPTY_STRING, field);
    }

    public static BusinessError nullFieldError(final String field) {
        return new BusinessError(NULL_FIELD, field);
    }

    public static BusinessError inputTooShort(final String field) {
        return new BusinessError(INPUT_TOO_SHORT, field);
    }

    public static BusinessError invalidEmail() {
        return new BusinessError(INVALID_EMAIL_FORMAT, EMAIL_FIELD);
    }

    public static BusinessError minFieldError(final String field) {
        return new BusinessError(MIN_VALUE, field);
    }

    public static BusinessError notFoundError(final String field) {
        return new BusinessError(NOT_FOUND, field);
    }

    public static BusinessError blankStringError(final String field) {
        return new BusinessError(BLANK_STRING, field);
    }

    public static BusinessError internalServerError() {
        return new BusinessError(INTERNAL_SERVER_ERROR);
    }

    public static BusinessError emptyCollection(final String field) {
        return new BusinessError(EMPTY_COLLECTION, field);
    }
}
