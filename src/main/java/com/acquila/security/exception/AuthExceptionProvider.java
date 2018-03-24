package com.acquila.security.exception;

import java.util.List;

import com.acquila.common.validation.exception.AcquilaException;
import com.acquila.common.validation.exception.BusinessError;

import static com.acquila.security.exception.AuthExceptionType.PARSING_ACCOUNT_FROM_TOKEN_FAILED;
import static com.acquila.security.exception.AuthExceptionType.SESSION_NOT_FOUND_FOR_TOKEN;
import static com.acquila.security.exception.AuthExceptionType.TOKEN_GENERATION_FAILED;

/**
 * Factory class responsible of building exception instances.
 */
public class AuthExceptionProvider {

    public static AcquilaException tokenGenerationValidationException(final List<BusinessError> errorList) {
        return new AcquilaException(TOKEN_GENERATION_FAILED, errorList);
    }

    public static AcquilaException tokenNotFoundOrSessionExpired(final BusinessError error) {
        return new AcquilaException(SESSION_NOT_FOUND_FOR_TOKEN, error);
    }

    public static AcquilaException failedToParseTokenData(final BusinessError error) {
        return new AcquilaException(PARSING_ACCOUNT_FROM_TOKEN_FAILED, error);
    }
}
