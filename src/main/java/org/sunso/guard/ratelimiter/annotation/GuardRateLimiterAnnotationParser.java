package org.sunso.guard.ratelimiter.annotation;

import org.sunso.guard.ratelimiter.interceptor.GuardRateLimiterAttribute;

import java.lang.reflect.AnnotatedElement;

/**
 * @RateLimiter注解的解析器
 */
public interface GuardRateLimiterAnnotationParser {
    default boolean isCandidateClass(Class<?> targetClass) {
        return true;
    }

    /**
     * 解析@RateLimiter注解的属性
     * @param annotatedElement
     * @return
     */
    GuardRateLimiterAttribute parseGuardRateLimiterAnnotation(AnnotatedElement annotatedElement);
}
