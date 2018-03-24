package com.acquila.security.service;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.acquila.common.validation.exception.BusinessError;
import com.acquila.common.validation.exception.GenericBusinessErrorType;
import com.acquila.security.configuration.AuthTestParent;
import com.acquila.security.dto.TokenGenerationDto;
import com.acquila.security.exception.AuthExceptionType;
import com.acquila.security.jwt.JwtTokenHandler;
import com.acquila.security.repository.AccountCacheRepository;
import com.acquila.security.repository.SessionCacheRepository;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class AuthServiceGenerationTest extends AuthTestParent {

    @Autowired
    private AuthService authService;

    @Autowired
    private SessionCacheRepository sessionCache;

    @Autowired
    private AccountCacheRepository accountCache;

    @Autowired
    private JwtTokenHandler jwtTokenHandler;

    @Test
    public void testGenerateTokenSuccess() {
        final TokenGenerationDto tokenGenerationDto = buildTokenGenerationDto();

        String token = authService.generateToken(tokenGenerationDto);

        assertTrue("Token not present in session cache", sessionCache.checkForKey(token));

        //test if token is added correctly to the account cache too
        assertTrue("Token not present in account cache", accountCache.checkForKey(tokenGenerationDto.getAccountId()));
        assertTrue(accountCache.getByKey(tokenGenerationDto.getAccountId()).contains(token));

        assertNotNull(token);
        assertNotNull(jwtTokenHandler.parseAccountFromToken(token));

    }

    @Test
    public void testGenerateToken_mandatory_fields() {
        TokenGenerationDto tokenGenerationDto = TokenGenerationDto.builder().build();

        try {
            authService.generateToken(tokenGenerationDto);
            fail("Exception should have been thrown.");
        } catch (Exception e) {

            List<BusinessError> errors = new ArrayList<>();
            errors.add(new BusinessError(GenericBusinessErrorType.EMPTY_STRING, "accountId"));
            errors.add(new BusinessError(GenericBusinessErrorType.EMPTY_STRING, "username"));
            errors.add(new BusinessError(GenericBusinessErrorType.EMPTY_STRING, "details"));

            testExceptionIsMappedCorrectly(AuthExceptionType.TOKEN_GENERATION_FAILED,
                    errors, e);
        }
    }
}
