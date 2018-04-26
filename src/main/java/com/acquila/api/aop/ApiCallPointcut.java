package com.acquila.api.aop;

import javax.servlet.http.HttpServletResponse;

import org.aspectj.lang.annotation.Pointcut;

/**
 * Pointcut for logging and translation aspects.
 */
public class ApiCallPointcut {

    @Pointcut("execution(* com.acquila.api.controller.AcquisitionController.*(..))  && args(response, ..)")
    void handleApiCall(final HttpServletResponse response) {
    }
}
