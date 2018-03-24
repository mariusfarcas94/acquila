package com.acquila.security.dto;

import org.hibernate.validator.constraints.NotEmpty;

import com.acquila.common.enumerated.Role;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Dto class used to hold account data to included in the JWT.
 */

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AccountDto {

    @NotEmpty
    private String username;

    @NotEmpty
    private String accountId;

    private long expires;

    private Role role;

}
