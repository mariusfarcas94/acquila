package com.acquila.security.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.acquila.common.enumerated.Role;
import com.acquila.security.configuration.AuthTestParent;
import com.acquila.security.dto.TokenGenerationDto;
import com.acquila.security.jwt.JwtTokenHandler;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class AuthUpdateRoleTest extends AuthTestParent {

    @Autowired
    private AuthService authService;

    @Autowired
    private JwtTokenHandler jwtTokenHandler;

    @Test
    public void testTokenRegenerationOnRoleChange() {
        final TokenGenerationDto tokenGenerationDto = buildTokenGenerationDto();
        tokenGenerationDto.setRole(Role.USER);

        final String token = authService.generateToken(tokenGenerationDto);

        assertNotNull(token);
        assertEquals(jwtTokenHandler.parseAccountFromToken(token).getRole(), Role.USER);

        //we set the new role on the token generation dto and call the updateRole method
        tokenGenerationDto.setRole(Role.ADMIN);
        authService.updateRole(tokenGenerationDto);

        final String updatedRoleToken = authService.verifyToken(token).getToken();

        assertNotNull(token);
        assertEquals(jwtTokenHandler.parseAccountFromToken(updatedRoleToken).getRole(), Role.ADMIN);
    }
}
