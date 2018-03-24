package com.acquila.core.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.acquila.common.enumerated.Role;
import com.acquila.core.entity.common.BaseEntity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Entity representing the account of a user.
 */
@Entity
@Getter
@Setter
@AllArgsConstructor
public class Account extends BaseEntity {

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private boolean enabled;

    @Column(nullable = false)
    private boolean emailConfirmed;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

}
