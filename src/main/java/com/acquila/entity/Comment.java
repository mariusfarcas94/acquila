package com.acquila.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.acquila.entity.common.BaseEntity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Entity representing a comment.
 */
@Entity
@Getter
@Setter
@AllArgsConstructor
public class Comment extends BaseEntity {

    @ManyToOne(optional = false)
    @JoinColumn(name = "acquisition_id")
    public Acquisition acquisition;

    @ManyToOne(optional = false)
    @JoinColumn(name = "account_id")
    private Account account;

    @Column(nullable = false)
    public String text;


}
