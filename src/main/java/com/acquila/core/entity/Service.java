package com.acquila.core.entity;

import javax.persistence.Column;
import javax.persistence.Entity;

import com.acquila.core.enumerated.AcquisitionType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static com.acquila.core.enumerated.AcquisitionType.SERVICE;

/**
 * Entity representing a direct acquisition of the type: services/goods.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Service extends DirectAcquisition {

    @Column(nullable = false, updatable = false)
    public int orderingNumber;

    public AcquisitionType getType() {
        return SERVICE;
    }
}
