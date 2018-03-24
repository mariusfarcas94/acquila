package com.acquila.entity;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.acquila.enumerated.ActionType;

/**
 * Entity representing an activity performed by the user on an acquisition.
 */
public class Activity {

    @ManyToOne(optional = false)
    @JoinColumn(name = "acquisition_id")
    public Acquisition acquisition;

    @ManyToOne(optional = false)
    @JoinColumn(name = "account_id")
    private Account account;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ActionType actionType;
}
