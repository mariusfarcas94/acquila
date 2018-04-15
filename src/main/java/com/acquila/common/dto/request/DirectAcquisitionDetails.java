package com.acquila.common.dto.request;

import java.math.BigDecimal;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import com.acquila.core.enumerated.AcquisitionType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Details needed for the creation of a direct acquisition.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DirectAcquisitionDetails {

    @NotBlank
    private String objective;

    @NotBlank
    private String cpvCode;

    @NotNull
    @Min(0)
    private BigDecimal estimatedValue;

    @NotBlank
    private String financingSource;

    @NotBlank
    private String initialDate;

    @NotBlank
    private String finalDate;

    @NotBlank
    private AcquisitionType type;
}
