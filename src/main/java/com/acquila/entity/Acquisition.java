package com.acquila.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import com.acquila.entity.common.BaseEntity;
import com.acquila.enumerated.AcquisitionStatus;
import com.acquila.enumerated.AcquisitionType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Entity representing an acquisition.
 */
@Entity
@Getter
@Setter
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Acquisition extends BaseEntity {

    @Column(nullable = false, updatable = false)
    private Account creator;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private AcquisitionStatus status;

    public abstract AcquisitionType getType();

}
