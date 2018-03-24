package com.acquila.security.repository;

import org.springframework.stereotype.Component;

import com.acquila.security.repository.cache.CacheRepository;
import com.hazelcast.core.HazelcastInstance;

import lombok.extern.log4j.Log4j2;

import static com.acquila.security.repository.CacheName.SESSION_CACHE;

/**
 * Repository implementation for session cache.
 */
@Component
@Log4j2
public class SessionCacheRepository extends CacheRepository<String, String> {

    public SessionCacheRepository(HazelcastInstance hazelcastInstance) {
        super(hazelcastInstance);
    }

    @Override
    protected String getCacheName() {
        return SESSION_CACHE.getCacheName();
    }
}
