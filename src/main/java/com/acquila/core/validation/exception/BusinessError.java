package com.acquila.core.validation.exception;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * Class that holds the details of a business error.
 */
@Getter
@EqualsAndHashCode
@ToString
public class BusinessError {

    private BusinessErrorType businessErrorType;

    private String field;

    private String message;


    public BusinessError(BusinessErrorType businessErrorType) {
        this(businessErrorType, "");
    }

    public BusinessError(BusinessErrorType businessErrorType, String field) {
        this(businessErrorType, field, businessErrorType.getMessage());
    }

    public BusinessError(BusinessErrorType businessErrorType, String field, String message) {
        this.businessErrorType = businessErrorType;
        this.field = field;
        this.message = message;
    }
}
