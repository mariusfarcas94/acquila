package com.acquila.core.entity;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.acquila.core.enumerated.AcquisitionType;
import com.acquila.core.enumerated.ProcedureStatus;

import static com.acquila.core.enumerated.AcquisitionType.PROCEDURE;

/**
 * Entity representing an acquisition of the type: procedure.
 */
public class Procedure extends Acquisition {

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ProcedureStatus status;

    @Column(nullable = false, length = 50)
    private String procedureType;

    @Override
    public AcquisitionType getType() {
        return PROCEDURE;
    }
}
