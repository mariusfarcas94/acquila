package com.acquila.common.validation.exception;

import static com.acquila.common.validation.exception.GenericBusinessErrorType.INVALID_CPV;

/**
 * Error provider for specific acquila errors.
 */
public class AcquilaErrorProvider {

    public static BusinessError invalidCpvCode() {
        return new BusinessError(INVALID_CPV);
    }
}
