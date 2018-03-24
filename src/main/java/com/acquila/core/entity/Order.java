package com.acquila.core.entity;

import javax.persistence.Column;
import javax.persistence.Entity;

import com.acquila.core.entity.common.BaseEntity;

/**
 * Entity holding the order numbers for all the types of acquisitions.
 */
@Entity
public class Order extends BaseEntity {

    @Column(nullable = false)
    private int servicesNumber;

    @Column(nullable = false)
    private int worksNumber;

//    private int getNextServiceNumber() {
//        return servicesNumber += 1;
//    }
//
//    private int getNextWorkNumber() {
//        return worksNumber += 1;
//    }
}
