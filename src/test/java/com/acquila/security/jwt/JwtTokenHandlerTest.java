package com.acquila.security.jwt;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.acquila.common.validation.exception.BusinessError;
import com.acquila.security.configuration.AuthTestParent;
import com.acquila.security.dto.AccountDto;
import com.acquila.security.exception.AuthBusinessErrorType;
import com.acquila.security.exception.AuthExceptionType;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

public class JwtTokenHandlerTest extends AuthTestParent {

    @Autowired
    private JwtTokenHandler jwtTokenHandler;

    @Test
    public void tokenGenerationTest() {

        AccountDto accountDto = buildTokenGenerationDto();
        String token = jwtTokenHandler.createTokenForAccount(accountDto);

        assertNotNull("Token should not be null", token);
        assertEquals("Wrong value of token", "eyJ1c2VybmFtZSI6InVzZXJuYW1lIiwiYWNjb3VudElkIjoiYWNjb3VudElkIiwiZXhwaXJlcyI6MC" +
                        "wicm9sZSI6IkFETUlOIn0=.0eMbY0LXwQ6S1o6TknjISZ2OtE5fDaCmjbsIoMLr7AY=",
                token);
    }

    @Test
    public void parseAccountFromTokenTest() {
        AccountDto accountDto = buildTokenGenerationDto();
        String token = jwtTokenHandler.createTokenForAccount(accountDto);

        AccountDto parsedAccount = jwtTokenHandler.parseAccountFromToken(token);

        assertEquals("AccountId should have the same value", parsedAccount.getAccountId(), accountDto.getAccountId());
        assertEquals("Username should have the same value", parsedAccount.getUsername(), accountDto.getUsername());
        assertEquals("Expires should have the same value", parsedAccount.getExpires(), accountDto.getExpires());
        assertEquals("Role should have the same value", parsedAccount.getRole(), accountDto.getRole());
    }

    @Test
    public void parseAccountFromTokenTest_null() {
        try {
            jwtTokenHandler.parseAccountFromToken(null);

            fail("Should not parse the token");
        } catch (Exception e) {
            BusinessError error = new BusinessError(AuthBusinessErrorType.SESSION_EXPIRED, "token");

            testExceptionIsMappedCorrectly(AuthExceptionType.PARSING_ACCOUNT_FROM_TOKEN_FAILED,
                    error, e);
        }
    }

    @Test
    public void parseAccountFromTokenTest_noDotToken() {
        try {
            AccountDto accountDto = buildTokenGenerationDto();
            String token = jwtTokenHandler.createTokenForAccount(accountDto);

            jwtTokenHandler.parseAccountFromToken(token.replaceAll("\\.", ""));

            fail("Should not parse the token");
        } catch (Exception e) {
            BusinessError error = new BusinessError(AuthBusinessErrorType.SESSION_EXPIRED, "token");

            testExceptionIsMappedCorrectly(AuthExceptionType.PARSING_ACCOUNT_FROM_TOKEN_FAILED,
                    error, e);
        }
    }

    @Test
    public void parseAccountFromTokenTest_wrongToken() {
        try {
            jwtTokenHandler.parseAccountFromToken("text.text");

            fail("Should not parse the token");
        } catch (Exception e) {
            BusinessError error = new BusinessError(AuthBusinessErrorType.SESSION_EXPIRED, "token");

            testExceptionIsMappedCorrectly(AuthExceptionType.PARSING_ACCOUNT_FROM_TOKEN_FAILED,
                    error, e);
        }
    }


}
