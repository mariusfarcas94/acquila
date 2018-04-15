package com.acquila.core.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Entity holding the order numbers for all the types of acquisitions.
 */
@Entity
public class OrderNumber {

    @Id
    @Column
    private int year;

    @Column(nullable = false)
    private int servicesNumber;

    @Column(nullable = false)
    private int worksNumber;

    @Column(nullable = false)
    private int proceduresNumber;

    public int getNextServiceNumber() {
        return servicesNumber++;
    }

    public int getNextWorkNumber() {
        return worksNumber++;
    }

    public int getNextProcedureNumber() {
        return proceduresNumber++;
    }

}
