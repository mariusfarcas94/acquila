package com.acquila.security.repository;

import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Component;

import com.acquila.security.repository.cache.CacheRepository;
import com.hazelcast.core.HazelcastInstance;

import lombok.extern.log4j.Log4j2;

import static org.hibernate.validator.internal.util.CollectionHelper.asSet;
import static com.acquila.security.repository.CacheName.ACCOUNT_CACHE;
import static com.acquila.security.service.impl.AuthServiceImpl.MINUTE_TIME_UNIT;

/**
 * Repository implementation for session cache.
 */
@Component
@Log4j2
public class AccountCacheRepository extends CacheRepository<String, Set<String>> {

    public AccountCacheRepository(HazelcastInstance hazelcastInstance) {
        super(hazelcastInstance);
    }

    @Override
    protected String getCacheName() {
        return ACCOUNT_CACHE.getCacheName();
    }

    /**
     * @inheritDoc
     */
    @Override
    public Set<String> putDataToCache(final String keyString, final Set<String> data) {
        getCacheMap().set(keyString, data);

        return data;
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public Set<String> putDataToCache(final String accountId, final Set<String> data, final int ttl, final TimeUnit timeUnit) {
        getCacheMap().lock(accountId);

        final Set<String> accountSessions = executeOnKey(accountId, sessions -> {
            if (sessions == null) {
                return data;
            }
            sessions.addAll(data);
            return sessions;
        });

        getCacheMap().set(accountId, accountSessions, ttl, timeUnit);

        getCacheMap().unlock(accountId);

        return accountSessions;
    }

    /**
     * Replaces an old token with a new one. The implementation uses locking to enforce thread safety.
     *
     * @param token             the token to be replaced.
     * @param newToken          the new token.
     * @param accountId         the accountId for which the token will be replaced.
     * @param sessionExpiration the new expiration time.
     */
    public void replaceTokenInCache(final String token, final String newToken, final String accountId, int sessionExpiration) {
        getCacheMap().lock(accountId);

        final Set<String> accountSessions = executeOnKey(accountId, sessions -> {
            if (sessions == null) {
                return asSet(newToken);
            }
            sessions.add(newToken);
            sessions.remove(token);
            return sessions;
        });

        getCacheMap().set(accountId, accountSessions, sessionExpiration, MINUTE_TIME_UNIT);

        getCacheMap().unlock(accountId);
    }

}
