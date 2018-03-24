package com.acquila.core.validation.exception;

import java.util.List;

import static com.acquila.core.validation.exception.CommonAcquilaExceptionType.ILLEGAL_ARGUMENT_EXCEPTION;

/**
 * Exception provider for generic exception that do not belong to a specific aggregate.
 */
public class AcquilaExceptionProvider {

    public static AcquilaException illegalArgumentsException(final BusinessError error) {
        return new AcquilaException(ILLEGAL_ARGUMENT_EXCEPTION, error);
    }

    public static AcquilaException illegalArgumentsException(final List<BusinessError> errors) {
        return new AcquilaException(ILLEGAL_ARGUMENT_EXCEPTION, errors);
    }

}
