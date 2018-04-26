package com.acquila.core.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.acquila.core.enumerated.AcquisitionType;
import com.acquila.core.enumerated.ProcedureStatus;

import lombok.Getter;
import lombok.Setter;

import static com.acquila.core.enumerated.AcquisitionType.PROCEDURE;

/**
 * Entity representing an acquisition of the type: procedure.
 */
@Entity
@Getter
@Setter
public class Procedure extends Acquisition {

    @Column(nullable = false, updatable = false)
    public int orderingNumber;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ProcedureStatus status;

    @Column(nullable = false, length = 50)
    private String procedureType;

    @Column(nullable = false, length = 50)
    private String estimatedPeriod;

    @Override
    public AcquisitionType getType() {
        return PROCEDURE;
    }
}
