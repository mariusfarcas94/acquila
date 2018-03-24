package com.acquila.security.dto;

import com.acquila.common.enumerated.Role;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * Dto that hold the response data for token verification.
 */

@Setter
@Getter
@Builder
public class TokenVerificationResponseDto {

    private String token;

    private String accountId;

    private Role role;
}
