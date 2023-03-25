package org.sunso.guard.ratelimiter.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.sunso.guard.ratelimiter.annotation.AnnotationGuardRateLimiterAttributeSource;
import org.sunso.guard.ratelimiter.annotation.RateLimiter;
import org.sunso.guard.ratelimiter.interceptor.GuardRateLimiterAspectSupport;


/**
 * AOP拦截器
 */
@Aspect
public class AnnotationRateLimiterAspect extends GuardRateLimiterAspectSupport {
    public AnnotationRateLimiterAspect() {
        setGuardRateLimiterAttributeSource(new AnnotationGuardRateLimiterAttributeSource());
    }

    @Pointcut("@annotation(org.sunso.guard.ratelimiter.annotation.RateLimiter)")
    public void rateLimiter() {
    }

    @Around("@annotation(rateLimiter)")
    public Object around(ProceedingJoinPoint joinPoint, RateLimiter rateLimiter) {
        try {
            MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
            return invokeWithRateLimiter(methodSignature.getMethod(), joinPoint.getTarget().getClass(), new InvocationCallback() {
                @Override
                public Object proceedWithInvocation() throws Throwable {
                    return joinPoint.proceed();
                }

                @Override
                public Object getTarget() {
                    return joinPoint.getTarget();
                }

                @Override
                public Object[] getArgs() {
                    return joinPoint.getArgs();
                }
            });
        }
        catch (RuntimeException | Error ex) {
            throw ex;
        }
        catch (Throwable thr) {
            throw new IllegalStateException("Should never get here", thr);
        }
    }
}
