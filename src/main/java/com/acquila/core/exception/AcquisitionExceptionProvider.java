package com.acquila.core.exception;

import java.util.List;

import com.acquila.common.validation.exception.AcquilaException;
import com.acquila.common.validation.exception.BusinessError;

import static com.acquila.core.exception.AcquisitionExceptionType.ACQUISITION_CREATION_FAILED;
import static com.acquila.core.exception.AcquisitionExceptionType.ACQUISITION_RETRIEVAL_FAILED;

/**
 * Factory class for acquisition exceptions.
 */
public class AcquisitionExceptionProvider {

    public static AcquilaException getAcquisitionsException(final BusinessError error) {
        return new AcquilaException(ACQUISITION_RETRIEVAL_FAILED, error);
    }

    public static AcquilaException getAcquisitionsException(final List<BusinessError> errors) {
        return new AcquilaException(ACQUISITION_RETRIEVAL_FAILED, errors);
    }

    public static AcquilaException createAcquisitionException(final BusinessError error) {
        return new AcquilaException(ACQUISITION_CREATION_FAILED, error);
    }

    public static AcquilaException createAcquisitionException(final List<BusinessError> errors) {
        return new AcquilaException(ACQUISITION_CREATION_FAILED, errors);
    }
}
