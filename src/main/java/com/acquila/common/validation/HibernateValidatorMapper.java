package com.acquila.common.validation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

import javax.validation.ConstraintViolation;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.validation.metadata.ConstraintDescriptor;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import com.acquila.common.validation.exception.BusinessError;
import com.acquila.common.validation.exception.BusinessErrorType;

import static java.lang.String.format;
import static java.util.stream.Collectors.toList;
import static com.acquila.common.validation.exception.GenericBusinessErrorType.BLANK_STRING;
import static com.acquila.common.validation.exception.GenericBusinessErrorType.EMPTY_STRING;
import static com.acquila.common.validation.exception.GenericBusinessErrorType.INPUT_TOO_SHORT;
import static com.acquila.common.validation.exception.GenericBusinessErrorType.INVALID_EMAIL_FORMAT;
import static com.acquila.common.validation.exception.GenericBusinessErrorType.INVALID_INPUT_SIZE;
import static com.acquila.common.validation.exception.GenericBusinessErrorType.MAX_VALUE;
import static com.acquila.common.validation.exception.GenericBusinessErrorType.MIN_VALUE;
import static com.acquila.common.validation.exception.GenericBusinessErrorType.NULL_FIELD;


/**
 * Utility class used to map exceptions thrown by the hibernate validator to custom application errors.
 */
class HibernateValidatorMapper {

    private static final String INVALID_ERROR_MAPPING = "Invalid error mapping for annotation type %s";

    private static final Function<ConstraintDescriptor, BusinessErrorType> INVALID_LENGTH_ERROR_PROVIDER = (descriptor) -> {
        Length annotation = (Length) descriptor.getAnnotation();
        if (annotation.max() < Integer.MAX_VALUE) {
            return INVALID_INPUT_SIZE;
        } else if (annotation.min() > 0) {
            return INVALID_INPUT_SIZE;
        }
        return INVALID_INPUT_SIZE;
    };

    private static final Map<Class, Function<ConstraintDescriptor, BusinessErrorType>> ERROR_MAPPING = new HashMap<>();

    static {
        ERROR_MAPPING.put(NotNull.class, (descriptor) -> NULL_FIELD);
        ERROR_MAPPING.put(NotEmpty.class, (descriptor) -> EMPTY_STRING);
        //NotBlank unlike NotEmpty also trims the string
        ERROR_MAPPING.put(NotBlank.class, (descriptor) -> BLANK_STRING);
        ERROR_MAPPING.put(Length.class, INVALID_LENGTH_ERROR_PROVIDER);
        ERROR_MAPPING.put(Size.class, (descriptor) -> INPUT_TOO_SHORT);
        ERROR_MAPPING.put(Min.class, (descriptor) -> MIN_VALUE);
        ERROR_MAPPING.put(Max.class, (descriptor) -> MAX_VALUE);
        ERROR_MAPPING.put(Email.class, (descriptor) -> INVALID_EMAIL_FORMAT);
    }

    /**
     * Using this method we can control the error mapping in the applications where we use this library by providing
     * application specific mapping.
     * <p>
     * Based on the supplied keys we can also override the default mappings.
     */
    protected static void addAdditionalErrorMappings(Map<Class, Function<ConstraintDescriptor, BusinessErrorType>> mapping) {
        ERROR_MAPPING.putAll(mapping);
    }

    protected static <T> List<BusinessError> toBusinessErrors(final Set<ConstraintViolation<T>> constraintViolations) {
        return constraintViolations.stream()
                .map(cv -> {
                    final BusinessErrorType errorType = getBusinessErrorType(cv.getConstraintDescriptor());
                    return new BusinessError(errorType, cv.getPropertyPath().toString());
                })
                .collect(toList());
    }

    private static BusinessErrorType getBusinessErrorType(final ConstraintDescriptor<?> constraintDescriptor) {
        Function<ConstraintDescriptor, BusinessErrorType> errorProvider =
                ERROR_MAPPING.get(constraintDescriptor.getAnnotation().annotationType());

        ObjectValidator.throwIfNull(errorProvider,
                () -> new IllegalStateException(format(INVALID_ERROR_MAPPING, constraintDescriptor.getAnnotation().annotationType())));

        return errorProvider.apply(constraintDescriptor);
    }
}
