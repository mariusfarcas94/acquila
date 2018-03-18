package com.acquila.validation.exception;

import java.util.List;

import lombok.Getter;
import lombok.ToString;

import static java.util.Collections.singletonList;

/**
 * The common base exception used for all the business errors.
 */
@ToString
@Getter
public class AcquilaException extends RuntimeException {

    private AcquilaExceptionType exceptionType;

    private List<BusinessError> errorList;

    public AcquilaException(AcquilaExceptionType exceptionType, BusinessError error) {
        this(exceptionType, singletonList(error));
    }

    public AcquilaException(AcquilaExceptionType exceptionType, List<BusinessError> errorList) {
        super(exceptionType.getMessage());
        this.exceptionType = exceptionType;
        this.errorList = errorList;
    }

    public boolean hasError(BusinessError error) {
        return errorList.contains(error);
    }

    public boolean hasSingleError(BusinessError error) {
        return errorList.size() == 1 && hasError(error);
    }
}
