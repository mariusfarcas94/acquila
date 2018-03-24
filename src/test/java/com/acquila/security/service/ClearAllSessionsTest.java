package com.acquila.security.service;


import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.acquila.common.enumerated.Role;
import com.acquila.security.configuration.AuthTestParent;
import com.acquila.security.dto.TokenGenerationDto;
import com.acquila.security.repository.AccountCacheRepository;
import com.acquila.security.repository.SessionCacheRepository;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class ClearAllSessionsTest extends AuthTestParent {

    private static final String EXCEPTION_SHOULD_NOT_HAVE_BEEN_THROWN = "Exception should not have been thrown!";
    private static final String ACCOUNT_ID = "accountId";
    private static final String TOKEN = "token";

    @Autowired
    private AuthService authService;

    @Autowired
    private SessionCacheRepository sessionCache;

    @Autowired
    private AccountCacheRepository accountCache;

    @Test
    public void givenAccountWithSession_whenClearingAllSessions_sessionsAreCleared() {
        final TokenGenerationDto tokenGenerationDto = buildTokenGenerationDto();
        tokenGenerationDto.setRole(Role.USER);

        final String token = authService.generateToken(tokenGenerationDto);

        assertTrue(accountCache.checkForKey(ACCOUNT_ID));
        assertTrue(sessionCache.checkForKey(token));

        authService.clearAllSessions(ACCOUNT_ID);

        assertFalse(accountCache.checkForKey(ACCOUNT_ID));
        assertFalse(sessionCache.checkForKey(token));
    }

    @Test
    public void givenAccountWithNoSessions_whenClearingAllSessions_noExceptionIsThrown() {

        //we make sure there are no sessions for this id
        authService.clearAllSessions(ACCOUNT_ID);

        assertFalse(accountCache.checkForKey(ACCOUNT_ID));
        assertFalse(sessionCache.checkForKey(TOKEN));

        try {
            authService.clearAllSessions(ACCOUNT_ID);
        } catch (final Exception e) {
            fail(EXCEPTION_SHOULD_NOT_HAVE_BEEN_THROWN);
        }
    }
}
