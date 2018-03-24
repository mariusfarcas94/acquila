package com.acquila.security.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.acquila.common.validation.exception.BusinessError;
import com.acquila.security.configuration.AuthTestParent;
import com.acquila.security.dto.TokenVerificationResponseDto;
import com.acquila.security.exception.AuthBusinessErrorType;
import com.acquila.security.exception.AuthExceptionType;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

public class AuthServiceLogoutTest extends AuthTestParent {

    @Autowired
    private AuthService authService;

    @Test
    public void testLogoutSuccess() {

        // Login
        String token = authService.generateToken(buildTokenGenerationDto());

        TokenVerificationResponseDto responseDto = authService.verifyToken(token);
        assertNotNull(responseDto);

        // Logout
        authService.logout(token);

        try {
            authService.verifyToken(token);
            fail("Exception should have been thrown.");
        } catch (Exception e) {
            BusinessError error = new BusinessError(AuthBusinessErrorType.SESSION_EXPIRED, "token");

            testExceptionIsMappedCorrectly(AuthExceptionType.SESSION_NOT_FOUND_FOR_TOKEN,
                    error, e);
        }
    }

}
