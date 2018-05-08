package com.acquila.core.enumerated;

import java.math.BigDecimal;
import java.util.Arrays;

import lombok.AllArgsConstructor;
import lombok.Getter;

import static com.acquila.common.validation.exception.AcquilaErrorProvider.invalidCpvCode;
import static com.acquila.common.validation.exception.AcquilaExceptionProvider.illegalArgumentsException;

@Getter
@AllArgsConstructor
public enum Limit {

    WORK(new BigDecimal(441000)),
    SERVICE(new BigDecimal(132000));

    private BigDecimal value;
}
