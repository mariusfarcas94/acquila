package com.acquila.core.validation.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Common error type object.
 */
@AllArgsConstructor
@Getter
public class CommonBusinessErrorType implements BusinessErrorType {

    private int code;

    private String message;

}
