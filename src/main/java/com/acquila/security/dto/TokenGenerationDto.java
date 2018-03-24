package com.acquila.security.dto;


import org.hibernate.validator.constraints.NotEmpty;

import com.acquila.core.enumerated.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Dto class that holds all the information needed to generate the JWT token.
 */
@Setter
@Getter
@NoArgsConstructor
public class TokenGenerationDto extends AccountDto {

    @NotEmpty
    @JsonIgnore
    private String details;

    @Builder
    public TokenGenerationDto(String details, String username, String accountId,
                              long expires, Role role) {
        super(username, accountId, expires, role);
        this.details = details;
    }

}
