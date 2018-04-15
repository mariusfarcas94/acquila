package com.acquila.core.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import com.acquila.core.enumerated.DirectAcquisitionStatus;

import lombok.Getter;

/**
 * Abstract class representing a direct acquisition.
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Getter
public abstract class DirectAcquisition extends Acquisition {

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private DirectAcquisitionStatus status;

    @Column(nullable = false)
    private String initialDate;

    @Column(nullable = false)
    private String finalDate;

    public abstract int getOrderingNumber();
}
