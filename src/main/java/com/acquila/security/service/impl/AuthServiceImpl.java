package com.acquila.security.service.impl;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.acquila.core.enumerated.Role;
import com.acquila.security.dto.AccountDto;
import com.acquila.security.dto.TokenGenerationDto;
import com.acquila.security.dto.TokenVerificationResponseDto;
import com.acquila.security.exception.AuthExceptionProvider;
import com.acquila.security.jwt.JwtTokenHandler;
import com.acquila.security.repository.AccountCacheRepository;
import com.acquila.security.repository.SessionCacheRepository;
import com.acquila.security.service.AuthService;

import lombok.extern.log4j.Log4j2;

import static org.apache.logging.log4j.util.Strings.isEmpty;
import static org.hibernate.validator.internal.util.CollectionHelper.asSet;
import static org.springframework.util.CollectionUtils.isEmpty;
import static com.acquila.core.validation.ObjectValidator.throwIfInvalid;
import static com.acquila.security.exception.AuthExceptionProvider.tokenNotFoundOrSessionExpired;
import static com.acquila.security.exception.BusinessErrorProvider.sessionExpired;

/**
 * {@inheritDoc}.
 */
@Log4j2
@Service
public class AuthServiceImpl implements AuthService {

    public static final int SECONDS_TO_KEEP_TOKEN_IN_CACHE_AFTER_EXPIRATION = 10;
    public static final TimeUnit MINUTE_TIME_UNIT = TimeUnit.MINUTES;

    private final JwtTokenHandler jwtTokenHandler;

    private final SessionCacheRepository sessionCache;

    private final AccountCacheRepository accountCache;

    @Value("${jwt.token.expiration}")
    private int tokenExpiration;

    @Value("${account.session.expiration}")
    private int sessionExpiration;

    public AuthServiceImpl(JwtTokenHandler jwtTokenHandler,
                           SessionCacheRepository sessionCache,
                           AccountCacheRepository accountCache) {
        this.jwtTokenHandler = jwtTokenHandler;
        this.sessionCache = sessionCache;
        this.accountCache = accountCache;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String generateToken(final TokenGenerationDto tokenGenerationDto) {

        throwIfInvalid(tokenGenerationDto, AuthExceptionProvider::tokenGenerationValidationException);

        tokenGenerationDto.setExpires(getExpirationTime());

        final String token = jwtTokenHandler.createTokenForAccount(tokenGenerationDto);

        // put user token in cache
        saveDataToCache(token, tokenGenerationDto.getAccountId());

        return token;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TokenVerificationResponseDto verifyToken(String token) {
        AccountDto parsedAuthToken;

        if (token == null || !isLoggedIn(token)) {
            throw tokenNotFoundOrSessionExpired(sessionExpired());
        } else {
            parsedAuthToken = jwtTokenHandler.parseAccountFromToken(token);
        }

        final String secondToken = sessionCache.getByKey(token);
        final boolean secondTokenExists = !isEmpty(secondToken);

        //There is the case of 'simultaneous' asynchronous calls from UI for a user.

        if (isTokenExpired(parsedAuthToken)) {
            //
            //Those come with the same token as Request header.
            //
            //First call generates a new token and is returned to the UI. => So, the other calls will fail, as the old token
            //is not in the cache anymore. As a solution, the old token is kept 10 more seconds in the cache.
            //
            //When we generate a new token, it is associated with the old one and its ttl is set to 10 seconds. The association is made
            //by means of key=>value (oldToken => newToken). This also prevents the authentication filter to generate a new token for the
            //user at each one of the initial calls.

            //check if value exists for token.

            if (secondTokenExists) {

                return buildTokenVerificationResponse(secondToken, parsedAuthToken);
            }

            parsedAuthToken.setExpires(getExpirationTime());
            final String newToken = jwtTokenHandler.createTokenForAccount(parsedAuthToken);

            replaceTokenKeepingShortLived(token, newToken, parsedAuthToken.getAccountId());

            token = newToken;

        } else {
            if (secondTokenExists) {
                if (tokenIsYetValid(parsedAuthToken)) {
                    replaceTokenKeepingShortLived(token, secondToken, parsedAuthToken.getAccountId());
                }

                token = secondToken;
            }
        }

        saveDataToCache(token, parsedAuthToken.getAccountId());

        return buildTokenVerificationResponse(token, parsedAuthToken);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void logout(final String token) {
        final AccountDto parsedAuthToken = jwtTokenHandler.parseAccountFromToken(token);

        if (!sessionCache.clearByKey(token)) {
            log.debug("[Logout] Token not found: " + token);
        }

        removeFromAccountCache(token);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void clearAllSessions(String accountId) {
        final Set<String> accountSessions = accountCache.getByKeyWithLock(accountId);

        if (!isEmpty(accountSessions)) {
            accountSessions.forEach(sessionCache::clearByKey);
        }

        accountCache.clearByKey(accountId);

        accountCache.unlockForKey(accountId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateRole(final TokenGenerationDto tokenGenerationDto) {
        throwIfInvalid(tokenGenerationDto, AuthExceptionProvider::tokenGenerationValidationException);

        tokenGenerationDto.setExpires(getExpirationTime());

        final String tokenWithNewRole = jwtTokenHandler.createTokenForAccount(tokenGenerationDto);

        final Set<String> userTokens = new HashSet<>();

        if (accountCache.checkForKey(tokenGenerationDto.getAccountId())) {
            userTokens.addAll(accountCache.getByKey(tokenGenerationDto.getAccountId()));
        }

        userTokens.forEach(token -> sessionCache.putDataToCache(token, tokenWithNewRole));
    }

    private long getExpirationTime() {
        return Instant.now().toEpochMilli() + MINUTE_TIME_UNIT.toMillis(tokenExpiration);
    }

    private void saveDataToCache(final String token, final String accountId) {
        sessionCache.putDataToCache(token, "", sessionExpiration, MINUTE_TIME_UNIT);
        accountCache.putDataToCache(accountId, asSet(token), sessionExpiration, MINUTE_TIME_UNIT);
    }

    private boolean isLoggedIn(String token) {
        return sessionCache.checkForKey(token);
    }

    private void removeFromAccountCache(final String token) {
        final String accountId = jwtTokenHandler.parseAccountFromToken(token).getAccountId();

        accountCache.executeOnKey(accountId, sessions -> {
            if (sessions == null || !sessions.remove(token)) {
                log.debug("[Logout] Token not found in account sessions: " + token);
            }
            return sessions;
        });
    }

    private void replaceTokenKeepingShortLived(final String oldToken, final String newToken, final String accountId) {
        sessionCache.putDataToCache(oldToken, newToken, SECONDS_TO_KEEP_TOKEN_IN_CACHE_AFTER_EXPIRATION, TimeUnit.SECONDS);

        accountCache.replaceTokenInCache(oldToken, newToken, accountId, sessionExpiration);
    }

    private boolean isTokenExpired(final AccountDto accountDto) {
        return accountDto.getExpires() < Instant.now().toEpochMilli();
    }

    private Role extractRoleFromToken(final String token) {
        return jwtTokenHandler.parseAccountFromToken(token).getRole();
    }

    private TokenVerificationResponseDto buildTokenVerificationResponse(final String token, final AccountDto account) {
        return TokenVerificationResponseDto.builder()
                .token(token)
                .accountId(account.getAccountId())
                .role(extractRoleFromToken(token))
                .build();
    }

    private boolean tokenIsYetValid(final AccountDto account) {
        return account.getExpires() - Instant.now().toEpochMilli() > SECONDS_TO_KEEP_TOKEN_IN_CACHE_AFTER_EXPIRATION;
    }
}
