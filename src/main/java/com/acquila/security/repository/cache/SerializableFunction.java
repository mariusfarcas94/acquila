package com.acquila.security.repository.cache;

import java.io.Serializable;
import java.util.function.Function;

/**
 * This is a serializable function so we will be able to execute lambdas on distributed hazelcast cache.
 * <p>
 * This will be used on executeOnKey operation but for the caller there should not be any difference between
 * this and a function.
 *
 * @param <T> the type of the input to the function
 * @param <R> the type of the result of the function
 */
public interface SerializableFunction<T, R> extends Serializable, Function<T, R> {

}
