package com.acquila.entity;

import javax.persistence.Column;
import javax.persistence.Entity;

import com.acquila.entity.common.BaseEntity;
import com.acquila.enumerated.Role;

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
    private Role role;

}
