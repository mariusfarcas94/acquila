package com.acquila.common.dto.response;

import java.math.BigDecimal;

import com.acquila.core.enumerated.AcquisitionType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Centralized data based on a CPV code.
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class CentralizedDetails {

    private int year;

    private String cpvCode;

    private String cpvCodeDescription;

    private long numberOfContracts;

    private BigDecimal totalValue;

    private BigDecimal limit;

    private AcquisitionType type;

    public CentralizedDetails(final String cpvCode,
                              final String cpvCodeDescription,
                              final long numberOfContracts,
                              final BigDecimal totalValue,
                              final AcquisitionType type) {
        this.cpvCode = cpvCode;
        this.cpvCodeDescription = cpvCodeDescription;
        this.numberOfContracts = numberOfContracts;
        this.totalValue = totalValue;
        this.type = type;
    }
}
