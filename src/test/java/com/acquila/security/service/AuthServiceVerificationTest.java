package com.acquila.security.service;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.util.ReflectionTestUtils;

import com.acquila.common.validation.exception.BusinessError;
import com.acquila.security.configuration.AuthTestParent;
import com.acquila.security.dto.TokenGenerationDto;
import com.acquila.security.dto.TokenVerificationResponseDto;
import com.acquila.security.exception.AuthBusinessErrorType;
import com.acquila.security.exception.AuthExceptionType;
import com.acquila.security.jwt.JwtTokenHandler;
import com.acquila.security.repository.AccountCacheRepository;
import com.acquila.security.repository.SessionCacheRepository;
import com.acquila.security.service.impl.AuthServiceImpl;

import lombok.extern.log4j.Log4j2;

import static org.hibernate.validator.internal.util.CollectionHelper.asSet;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

@Log4j2
public class AuthServiceVerificationTest extends AuthTestParent {

    @Autowired
    private JwtTokenHandler jwtTokenHandler;
    @Autowired
    private SessionCacheRepository sessionCache;
    @Autowired
    private AccountCacheRepository accountCache;
    @InjectMocks
    private AuthService authService = new AuthServiceImpl(null, null, null);
    private int tokenExpiration = 1;
    private int sessionExpiration = 3;

    protected static void setFinalStatic(Field field, Object newValue) throws Exception {
        AccessController.doPrivileged((PrivilegedAction<Object>) () ->
        {
            try {

                field.setAccessible(true);
                Field modifiersField = Field.class.getDeclaredField("modifiers");
                modifiersField.setAccessible(true);
                modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);
                field.set(null, newValue);

            } catch (IllegalAccessException | NoSuchFieldException e) {
                fail("Failed to set private final static field!");
            }

            return null;
        });
    }

    @Before
    public void setUp() throws Exception {

        MockitoAnnotations.initMocks(this);

        ReflectionTestUtils.setField(authService, "jwtTokenHandler", jwtTokenHandler);
        ReflectionTestUtils.setField(authService, "sessionCache", sessionCache);
        ReflectionTestUtils.setField(authService, "accountCache", accountCache);
        ReflectionTestUtils.setField(authService, "tokenExpiration", tokenExpiration);
        ReflectionTestUtils.setField(authService, "sessionExpiration", sessionExpiration);

        setFinalStatic(AuthServiceImpl.class.getDeclaredField("MINUTE_TIME_UNIT"), TimeUnit.SECONDS);
        setFinalStatic(
                AuthServiceImpl.class.getDeclaredField("SECONDS_TO_KEEP_TOKEN_IN_CACHE_AFTER_EXPIRATION"), 1);
    }

    @Test
    public void testVerifyUserLoggedIn() {

        TokenGenerationDto tokenGenerationDto = buildTokenGenerationDto();
        String token = authService.generateToken(tokenGenerationDto);

        assertNotNull(token);

        TokenVerificationResponseDto response = authService.verifyToken(token);
        assertEquals("Failed to return the right accountId",
                tokenGenerationDto.getAccountId(), response.getAccountId());
        assertEquals("Failed to return the right role", tokenGenerationDto.getRole(), response.getRole());
    }

    @Test
    public void testVerifyUserExpire() throws InterruptedException {

        TokenGenerationDto tokenGenerationDto = buildTokenGenerationDto();
        String token = authService.generateToken(tokenGenerationDto);

        Thread.sleep(sessionExpiration * 1000 + 1);

        assertNotNull(token);

        try {
            authService.verifyToken(token);

            fail("Session should be expired");
        } catch (Exception e) {
            BusinessError error = new BusinessError(AuthBusinessErrorType.SESSION_EXPIRED, "token");

            testExceptionIsMappedCorrectly(AuthExceptionType.SESSION_NOT_FOUND_FOR_TOKEN,
                    error, e);
        }
    }

    @Test
    public void testConcurrentVerificationOfToken() throws InterruptedException {

        TokenGenerationDto dto1 = buildTokenGenerationDto();
        TokenGenerationDto dto2 = buildTokenGenerationDto();

        String token1 = authService.generateToken(dto1);
        String token2 = authService.generateToken(dto2);

        Thread.sleep(tokenExpiration * 2000 + 1);

        CountDownLatch latch = new CountDownLatch(3);

        Thread t1 = new Thread() {
            public void run() {
                latch.countDown();

                authService.verifyToken(token1);
            }
        };

        Thread t2 = new Thread() {
            public void run() {
                latch.countDown();

                authService.verifyToken(token2);
            }
        };

        t1.start();
        t2.start();

        latch.countDown();

        t1.join();
        t2.join();

        assertFalse(accountCache.getByKey(dto1.getAccountId()).contains(token1));
        assertFalse(accountCache.getByKey(dto1.getAccountId()).contains(token2));
    }

    @Test
    public void testVerifyTokenRegeneration() throws InterruptedException {

        TokenGenerationDto tokenGenerationDto = buildTokenGenerationDto();
        String token = authService.generateToken(tokenGenerationDto);
        assertNotNull(token);

        Thread.sleep(tokenExpiration * 1000 + 1);

        TokenVerificationResponseDto response = authService.verifyToken(token);
        assertNotNull(response);

        String newTokenBasedOnOld = sessionCache.getByKey(token);
        boolean newTokenExists = sessionCache.checkForKey(response.getToken());

        assertEquals("Failed to return the right accountId",
                tokenGenerationDto.getAccountId(), response.getAccountId());

        assertEquals("Failed to return the right role",
                tokenGenerationDto.getRole(), response.getRole());

        assertNotEquals("Token should be different",
                token, response.getToken());

        assertEquals("Value of token in cache should be the new token.",
                newTokenBasedOnOld, response.getToken());

        assertTrue("New token should be in session cache", newTokenExists);

        assertTrue("New accountId should have an entry in account cache",
                accountCache.checkForKey(tokenGenerationDto.getAccountId()));

        assertTrue("New token should be in account cache",
                accountCache.getByKey(tokenGenerationDto.getAccountId()).contains(newTokenBasedOnOld));

        assertFalse("Old token should have been replaced",
                accountCache.getByKey(tokenGenerationDto.getAccountId()).contains(token));
    }

    @Test
    public void givenNoTokensInCache_whenReplaceToken_TokenIsInserted() {
        final String accountId = "accountId1";
        final String oldToken = "oldToken";
        final String newToken = "newToken";

        accountCache.replaceTokenInCache(oldToken, newToken, accountId, 30);

        final Set<String> result = accountCache.getByKey(accountId);

        assertEquals(1, result.size());
        assertFalse(result.contains(oldToken));
        assertTrue(result.contains(newToken));
    }

    @Test
    public void givenOldTokenExistsInCache_whenReplaceToken_TokenIsReplaced() {
        final String accountId = "accountId2";
        final String oldToken = "oldToken";
        final String newToken = "newToken";

        accountCache.putDataToCache(accountId, asSet(oldToken));

        accountCache.replaceTokenInCache(oldToken, newToken, accountId, 30);

        final Set<String> result = accountCache.getByKey(accountId);

        assertEquals(1, result.size());
        assertFalse(result.contains(oldToken));
        assertTrue(result.contains(newToken));
    }
}

