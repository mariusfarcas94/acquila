package com.acquila.security.exception;

import com.acquila.core.validation.exception.BusinessError;

import static com.acquila.security.exception.AuthBusinessErrorType.SESSION_EXPIRED;

/**
 * Factory class used to hide the creation logic of AcquilaException objects.
 */
public class BusinessErrorProvider {

    public static final String TOKEN = "token";

    public static BusinessError sessionExpired() {
        return new BusinessError(SESSION_EXPIRED, TOKEN);
    }
}
