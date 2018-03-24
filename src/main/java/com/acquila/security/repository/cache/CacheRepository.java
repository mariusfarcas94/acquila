package com.acquila.security.repository.cache;

import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;

/**
 * * Repository managing adding and removing data form hazelcast in-memory caching.
 *
 * @param <K> generic key type.
 * @param <V> generic value type.
 */
public abstract class CacheRepository<K, V> {

    private final HazelcastInstance hazelcastInstance;

    private IMap<K, V> cacheMap;

    public CacheRepository(HazelcastInstance hazelcastInstance) {
        this.hazelcastInstance = hazelcastInstance;
    }

    @PostConstruct
    private void init() {
        cacheMap = hazelcastInstance.getMap(getCacheName());
    }

    protected abstract String getCacheName();

    protected IMap<K, V> getCacheMap() {
        return cacheMap;
    }

    /**
     * Put data to cache.
     */
    public V putDataToCache(K key, V value) {
        cacheMap.set(key, value);

        return value;
    }

    /**
     * Put data to cache (for a specified time).
     */
    public V putDataToCache(K key, V value, int ttl, TimeUnit timeUnit) {
        cacheMap.set(key, value, ttl, timeUnit);

        return value;
    }

    /**
     * Check if data exists in cache.
     */
    public boolean checkForKey(K key) {
        return cacheMap.containsKey(key);
    }

    /**
     * Delete the data from cache for a key.
     */
    public boolean clearByKey(K key) {
        return cacheMap.remove(key) != null;
    }

    /**
     * Retrieves data from cache for a key.
     */
    public V getByKey(K key) {
        return (V) cacheMap.get(key);
    }

    /**
     * * Retrieves data from cache for a key and locks the entry.
     */
    public V getByKeyWithLock(K key) {
        cacheMap.lock(key);
        return (V) cacheMap.get(key);
    }

    /**
     * Check if data exists in cache.
     */
    public void unlockForKey(K key) {
        cacheMap.unlock(key);
    }

    /**
     * Removes all data from cache.
     */
    public void clearCache() {
        cacheMap.clear();
    }

    /**
     * Perform atomically the provided operation on the value corresponding to the specified key.
     *
     * @param key            the specified key.
     * @param valueProcessor the provided operation.
     * @return the processed value.
     */
    public V executeOnKey(K key, SerializableFunction<V, V> valueProcessor) {
        Object result = cacheMap.executeOnKey(key,
                new CacheEntryProcessor<V, V>(valueProcessor));

        return (V) result;
    }

}
