package com.acquila.core.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.acquila.core.entity.common.BaseEntity;
import com.acquila.core.enumerated.CommentType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Entity representing a comment.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
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

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    public CommentType type;


}
