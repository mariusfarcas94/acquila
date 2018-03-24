package com.acquila.security.repository;

/**
 * Stores different types of cache names.
 */
public enum CacheName {

    SESSION_CACHE("sessions"),
    ACCOUNT_CACHE("accounts");

    private String cacheName;

    CacheName(String cacheName) {
        this.cacheName = cacheName;
    }

    public String getCacheName() {
        return cacheName;
    }

}
