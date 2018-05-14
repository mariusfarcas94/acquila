package com.acquila.common.dto.response;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * General information about an acquisition.
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AcquisitionDetailsResponse {

    public String id;

    public int orderingNumber;

    private String objective;

    private String status;

    private String cpvCode;

    private String creator;

    private BigDecimal estimatedValue;

    private String financingSource;

    private String initialDate;

    private String finalDate;

    private String procedureType;

}
