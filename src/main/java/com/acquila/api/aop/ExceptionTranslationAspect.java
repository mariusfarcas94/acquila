package com.acquila.api.aop;

import javax.servlet.http.HttpServletResponse;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import com.acquila.common.validation.exception.AcquilaException;

import lombok.extern.log4j.Log4j2;

/**
 * Account service API aspect. This aspect is used to handle exceptions and send on error translated exceptions.
 */
@Component
@Aspect
@Log4j2
public class ExceptionTranslationAspect implements Ordered {

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE + 1;
    }

    @Around(value = "ApiCallPointcut.handleApiCall(response)", argNames = "pjp, response")
    public Object translateException(final ProceedingJoinPoint pjp, final HttpServletResponse response) throws Throwable {
        try {
            return pjp.proceed();
        } catch (final Exception e) {
            log.error(e);
            // We set the response status to 500 (Internal Server Error)
            response.setStatus(500);

            //  If the thrown exception is an AcquilaException, then we add the error list to the response.
            if (e instanceof AcquilaException) {
                response.getWriter().append(e.getMessage());
            }
            return null;
        }
    }
}
