package com.acquila.security.configuration;


import java.util.List;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.acquila.common.enumerated.Role;
import com.acquila.common.validation.exception.AcquilaException;
import com.acquila.common.validation.exception.AcquilaExceptionType;
import com.acquila.common.validation.exception.BusinessError;
import com.acquila.security.dto.TokenGenerationDto;
import com.acquila.security.exception.AuthBusinessErrorType;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {AuthTestConfiguration.class})
@ActiveProfiles("test")
public abstract class AuthTestParent {

    public static void testExceptionIsMappedCorrectly(AcquilaExceptionType expectedExceptionType, Exception actual) {
        assertTrue(actual instanceof AcquilaException);
        AcquilaException actualException = (AcquilaException) actual;
        assertEquals(expectedExceptionType, actualException.getExceptionType());
    }

    public static void testExceptionIsMappedCorrectly(AcquilaExceptionType expectedExceptionType,
                                                      AuthBusinessErrorType expectedBusinessErrorType, Exception actual) {
        assertTrue(actual instanceof AcquilaException);
        AcquilaException actualException = (AcquilaException) actual;

        // check the exception type
        assertEquals(expectedExceptionType, actualException.getExceptionType());

        // check the business errors contained in the exception
        assertEquals(1, actualException.getErrorList().size());
        assertEquals(expectedBusinessErrorType, actualException.getErrorList().get(0).getBusinessErrorType());
    }

    public static void testExceptionIsMappedCorrectly(AcquilaExceptionType expectedExceptionType,
                                                      BusinessError expectedBusinessError, Exception actual) {
        assertTrue(actual instanceof AcquilaException);
        AcquilaException actualException = (AcquilaException) actual;

        // check the exception type
        assertEquals(expectedExceptionType, actualException.getExceptionType());

        // check the business errors contained in the exception
        assertEquals(1, actualException.getErrorList().size());
        assertEquals(expectedBusinessError, actualException.getErrorList().get(0));
    }

    public static void testExceptionIsMappedCorrectly(AcquilaExceptionType expectedExceptionType,
                                                      List<BusinessError> expectedBusinessErrors, Exception actual) {
        assertTrue(actual instanceof AcquilaException);
        AcquilaException actualException = (AcquilaException) actual;

        // check the exception type
        assertEquals(expectedExceptionType, actualException.getExceptionType());

        // check the business errors contained in the exception
        assertEquals(expectedBusinessErrors.size(), actualException.getErrorList().size());
        assertTrue(actualException.getErrorList().containsAll(expectedBusinessErrors));
    }

    public static TokenGenerationDto buildTokenGenerationDto() {
        return TokenGenerationDto.builder()
                .accountId("accountId")
                .username("username")
                .role(Role.ADMIN)
                .details("details")
                .build();
    }
}
