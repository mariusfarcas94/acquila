package com.acquila.core.entity;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import com.acquila.core.enumerated.DirectAcquisitionStatus;

/**
 * Abstract class representing a direct acquisition.
 */
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class DirectAcquisition extends Acquisition {

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private DirectAcquisitionStatus status;

    @Column(nullable = false)
    private String initialDate;

    @Column(nullable = false)
    private String finalDate;
}
