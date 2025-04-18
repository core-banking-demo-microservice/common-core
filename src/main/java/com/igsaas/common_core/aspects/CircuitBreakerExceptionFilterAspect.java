package com.igsaas.common_core.aspects;

import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class CircuitBreakerExceptionFilterAspect {
    private final CircuitBreakerProperty circuitBreakerProperty;

    public CircuitBreakerExceptionFilterAspect(CircuitBreakerProperty circuitBreakerProperty) {
        this.circuitBreakerProperty = circuitBreakerProperty;
    }


    @Around("execution(* *..fallback*(.., Throwable))")
    public Object wrapWithCustomFallback(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();
        if (args.length > 0) {
            if (args[args.length - 1] instanceof Throwable throwable && !shouldExecuteFallback(throwable) && !(throwable instanceof CallNotPermittedException)) {
                throw throwable;
            }
        }
        return joinPoint.proceed();
    }

    private boolean shouldExecuteFallback(Throwable throwable) {
        return circuitBreakerProperty.getRecordExceptions().contains(throwable.getClass().getName());
    }
}
