package com.acquila.security.jwt;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.acquila.security.dto.AccountDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.log4j.Log4j2;

import static com.acquila.security.exception.AuthExceptionProvider.failedToParseTokenData;
import static com.acquila.security.exception.BusinessErrorProvider.sessionExpired;


/**
 * Utility class that handles generation and parsing of JWT Token.
 */
@Log4j2
@Component
public class JwtTokenHandler {

    private static final String HMAC_ALGORITHM = "HmacSHA256";
    private static final String SEPARATOR = ".";
    private static final String SEPARATOR_SPLITTER = "\\.";

    private final Mac hmac;

    @Autowired
    public JwtTokenHandler(@Value("${jwt.token.secret}") String secret) {
        try {
            hmac = Mac.getInstance(HMAC_ALGORITHM);
            hmac.init(new SecretKeySpec(DatatypeConverter.parseBase64Binary(secret), HMAC_ALGORITHM));
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            log.fatal("Failed to initialize HMAC: " + e.getMessage(), e);
            throw new IllegalStateException(e);
        }
    }

    public String createTokenForAccount(AccountDto accountDto) {
        byte[] accountBytes = toJSON(accountDto);
        byte[] hash = createHmac(accountBytes);
        final StringBuilder sb = new StringBuilder(170);
        sb.append(toBase64(accountBytes));
        sb.append(SEPARATOR);
        sb.append(toBase64(hash));
        return sb.toString();
    }

    public AccountDto parseAccountFromToken(String token) {

        if (token == null) {
            throw failedToParseTokenData(sessionExpired());
        }

        final String[] parts = token.split(SEPARATOR_SPLITTER);
        if (parts.length == 2 && parts[0].length() > 0 && parts[1].length() > 0) {
            final byte[] accountBytes = fromBase64(parts[0]);
            final byte[] hash = fromBase64(parts[1]);

            boolean validHash = Arrays.equals(createHmac(accountBytes), hash);
            if (validHash) {
                return fromJSON(accountBytes);
            }
        }
        throw failedToParseTokenData(sessionExpired());
    }

    private AccountDto fromJSON(final byte[] accountBytes) {
        try {
            return new ObjectMapper().readValue(new ByteArrayInputStream(accountBytes), AccountDto.class);
        } catch (IOException e) {
            log.fatal("Failed to decode JSON from bytes: " + e.getMessage(), e);
            throw new IllegalStateException(e);
        }
    }

    private byte[] toJSON(AccountDto accountDto) {
        try {
            return new ObjectMapper().writeValueAsBytes(accountDto);
        } catch (JsonProcessingException e) {
            log.fatal("Failed to create json for user " + accountDto.getUsername(), e);
            throw new IllegalStateException(e);
        }
    }

    private String toBase64(byte[] content) {
        return DatatypeConverter.printBase64Binary(content);
    }

    private byte[] fromBase64(String content) {
        return DatatypeConverter.parseBase64Binary(content);
    }

    // synchronized to guard internal hmac object
    private synchronized byte[] createHmac(byte[] content) {
        return hmac.doFinal(content);
    }

}
