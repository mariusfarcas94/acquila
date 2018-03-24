package com.acquila.core.entity;

import javax.persistence.Column;
import javax.persistence.Entity;

import com.acquila.core.enumerated.AcquisitionType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import static com.acquila.core.enumerated.AcquisitionType.WORK;

/**
 * Entity representing an acquisition of the type: works.
 */
@Entity
@Getter
@Setter
@AllArgsConstructor
public class Work extends Acquisition {

    @Column(nullable = false, updatable = false)
    public int orderingNumber;

    //todo(mfarcas) - add other fields here

    public AcquisitionType getType() {
        return WORK;
    }
}
