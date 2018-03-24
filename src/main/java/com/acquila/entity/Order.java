package com.acquila.entity;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * Entity holding the order numbers for all the types of acquisitions.
 */
@Entity
public class Order {

    @Column(nullable = false)
    private int servicesNumber;

    @Column(nullable = false)
    private int worksNumber;

    private int getNextServiceNumber() {
        return servicesNumber += 1;
    }

    private int getNextWorkNumber() {
        return worksNumber += 1;
    }
}