package com.acquila.core.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import com.acquila.core.entity.common.BaseEntity;

/**
 * Entity holding the order numbers for all the types of acquisitions.
 */
@Entity
public class OrderNumber {

    @Id
    @Column
    private String year;

    @Column(nullable = false)
    private int servicesNumber;

    @Column(nullable = false)
    private int worksNumber;

    @Column(nullable = false)
    private int proceduresNumber;
}
