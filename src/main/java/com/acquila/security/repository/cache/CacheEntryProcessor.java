package com.acquila.security.repository.cache;

import java.util.Map;

import com.hazelcast.map.AbstractEntryProcessor;

/**
 * An entry processor that will be executed on a key in the distributed hazelcast cache.
 */
class CacheEntryProcessor<K, V> extends AbstractEntryProcessor<K, V> {

    private static final long serialVersionUID = -1492925635357218238L;

    private final SerializableFunction<V, V> valueProcessor;

    CacheEntryProcessor(SerializableFunction<V, V> valueProcessor) {
        this.valueProcessor = valueProcessor;
    }

    @Override
    public Object process(Map.Entry<K, V> entry) {
        V processedValue = valueProcessor.apply(entry.getValue());
        entry.setValue(processedValue);
        return processedValue;
    }
}
