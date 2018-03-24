package com.acquila.security.service;

import com.acquila.security.dto.TokenGenerationDto;
import com.acquila.security.dto.TokenVerificationResponseDto;

/**
 * Operations used to manage the auth token.
 */

public interface AuthService {

    /**
     * Generates a new JWT based on input data.
     *
     * @param tokenGenerationDto - the token generation details.
     * @return the generated token.
     */
    String generateToken(TokenGenerationDto tokenGenerationDto);

    /**
     * Checks if a user is authenticated based on the token received.
     * If the token is expired, a new one is returned.
     * If the role of the user was changed and a token with the new role was generated for him, the new one will be returned.
     * The old one is saved in the cache in case there are multiple requests in parallel with the old token.
     *
     * @param token - the token to be verified.
     * @return the token verification response.
     */
    TokenVerificationResponseDto verifyToken(String token);

    /**
     * Logs out the user with the given token.
     *
     * @param token - the token for which the session will be closed.
     */
    void logout(String token);

    /**
     * Removes all the open sessions for a given account identifier.
     *
     * @param accountId the provided account identifier.
     */
    void clearAllSessions(String accountId);

    /**
     * Create a new token for each session of a user in case the role of this user was changed.
     * This method is called in order to avoid logging out in order to get the new permissions in case of role change.
     *
     * @param tokenGenerationDto - the details about the updated account.
     */
    void updateRole(TokenGenerationDto tokenGenerationDto);

}
