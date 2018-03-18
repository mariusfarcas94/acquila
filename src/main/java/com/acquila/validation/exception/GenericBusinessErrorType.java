package com.acquila.validation.exception;

import lombok.Getter;

/**
 * Enum holding the error types and their codes.
 */
@Getter
public enum GenericBusinessErrorType implements BusinessErrorType {

    INTERNAL_SERVER_ERROR(500, "internal_server_error"),

    INVALID_UUID_FORMAT(100100, "invalid_uuid_format"),

    NULL_PARAMETER(100101, "null_parameter"),

    EMPTY_STRING(100102, "empty_string"),

    NULL_FIELD(100103, "null_field"),

    INPUT_TOO_SHORT(100104, "input_too_short"),

    INPUT_TOO_LONG(100105, "input_too_long"),

    INVALID_INPUT_SIZE(100106, "invalid_input_size"),

    MIN_VALUE(100107, "min_value"),

    MAX_VALUE(100108, "max_value"),

    INVALID_EMAIL_FORMAT(100109, "invalid_email_format"),

    NOT_FOUND(100110, "not_found"),

    BLANK_STRING(100111, "blank_string"),

    UNRECOGNIZED_ERROR_TYPE(100112, "unrecognized_error_type"),

    EMPTY_COLLECTION(100113, "empty_collection");


    private int code;
    private String message;

    GenericBusinessErrorType(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
