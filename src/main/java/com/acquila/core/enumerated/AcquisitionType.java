package com.acquila.core.enumerated;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Enum that holds the acquisition types.
 */
@AllArgsConstructor
@Getter
public enum AcquisitionType {

    SERVICE (new BigDecimal(132000)),

    WORK (new BigDecimal(441000)),
    // procedures have no limit
    PROCEDURE (new BigDecimal(-1));

    private BigDecimal value;
}
