package com.acquila.common.validation;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.metadata.ConstraintDescriptor;

import com.acquila.common.validation.exception.AcquilaException;
import com.acquila.common.validation.exception.BusinessError;
import com.acquila.common.validation.exception.BusinessErrorType;

import static java.util.Collections.singletonList;
import static com.acquila.common.validation.exception.GenericBusinessErrorProvider.nullParameterError;

/**
 * Utility class used for validating request dtos.
 * It will thrown the appropriate exceptions if needed.
 */
public class ObjectValidator {

    private static final Validator VALIDATOR = Validation.buildDefaultValidatorFactory().getValidator();

    public static void throwIfNull(final Object object, Supplier<RuntimeException> exceptionSupplier) {
        if (object == null) {
            throw exceptionSupplier.get();
        }
    }

    public static void throwIfEmpty(final String string, Supplier<RuntimeException> exceptionSupplier) {
        if (string == null || string.trim().length() == 0) {
            throw exceptionSupplier.get();
        }
    }

    public static void throwIfInvalid(final Object dto, Function<List<BusinessError>, AcquilaException> exceptionProvider) {
        throwIfNull(dto, () -> exceptionProvider.apply(singletonList(nullParameterError())));

        final List<BusinessError> errors = validateDto(dto);
        if (!errors.isEmpty()) {
            throw exceptionProvider.apply(errors);
        }
    }

    public static <T> List<BusinessError> validateDto(final T dto) {
        return HibernateValidatorMapper.toBusinessErrors(VALIDATOR.validate(dto));
    }

    public static void overrideErrorMapping(Map<Class, Function<ConstraintDescriptor, BusinessErrorType>> newMappings) {
        HibernateValidatorMapper.addAdditionalErrorMappings(newMappings);
    }
}
