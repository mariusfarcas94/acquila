package com.acquila.core.utility;

import java.time.OffsetDateTime;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.text.RandomStringGenerator;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.apache.commons.text.CharacterPredicates.DIGITS;
import static org.apache.commons.text.CharacterPredicates.LETTERS;

/**
 * Utility class for jwt operations.
 */
public class SecurityUtils {

    private static final int RANDOM_STRING_LENGTH = 100;

    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    private static final RandomStringGenerator randomStringGenerator = new RandomStringGenerator.Builder()
            .withinRange('0', 'z')
            .filteredBy(LETTERS, DIGITS)
            .build();


    public static String generateHashedCode(final String additionalString) {
        return DigestUtils.sha256Hex(generateRandomString() + OffsetDateTime.now() +
                additionalString);
    }

    public static String encodePassword(final String password) {
        return passwordEncoder.encode(password);
    }

    public static boolean passwordsMatch(final String password, final String hashedPassword) {
        return passwordEncoder.matches(password, hashedPassword);
    }

    public static String generateRandomString(final int length) {
        return randomStringGenerator.generate(length);
    }

    private static String generateRandomString() {
        return randomStringGenerator.generate(RANDOM_STRING_LENGTH);
    }

}
