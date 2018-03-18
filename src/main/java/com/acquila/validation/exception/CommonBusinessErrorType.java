package com.acquila.validation.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CommonBusinessErrorType implements BusinessErrorType {

    private int code;

    private String message;

}
