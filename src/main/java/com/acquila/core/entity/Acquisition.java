package com.acquila.core.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.acquila.core.entity.common.BaseEntity;
import com.acquila.core.enumerated.AcquisitionStatus;
import com.acquila.core.enumerated.AcquisitionType;

import lombok.Getter;
import lombok.Setter;

/**
 * Entity representing an acquisition.
 */
@Entity
@Getter
@Setter
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Acquisition extends BaseEntity {

    @ManyToOne(optional = false)
    @JoinColumn(name = "creator_id")
    private Account creator;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private AcquisitionStatus status;

    public abstract AcquisitionType getType();

}
