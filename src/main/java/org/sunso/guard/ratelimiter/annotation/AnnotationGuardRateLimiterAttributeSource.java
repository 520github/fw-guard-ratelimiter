package org.sunso.guard.ratelimiter.annotation;

import org.sunso.guard.ratelimiter.interceptor.AbstractGuardRateLimiterAttributeSource;
import org.sunso.guard.ratelimiter.interceptor.GuardRateLimiterAttribute;

import java.io.Serializable;
import java.lang.reflect.Method;

/**
 * 对@RateLimiter属性解析的资源封装
 */
public class AnnotationGuardRateLimiterAttributeSource extends AbstractGuardRateLimiterAttributeSource implements Serializable {

    private final boolean publicMethodsOnly;
    private final GuardRateLimiterAnnotationParser parser;

    public AnnotationGuardRateLimiterAttributeSource() {
        this(true);
    }
    public AnnotationGuardRateLimiterAttributeSource(boolean publicMethodsOnly) {
        this.publicMethodsOnly = publicMethodsOnly;
        parser = new SpringGuardRateLimiterAnnotationParser();
    }

    @Override
    protected GuardRateLimiterAttribute findGuardRateLimiterAttribute(Method method) {
        return parser.parseGuardRateLimiterAnnotation(method);
    }

    @Override
    public boolean isCandidateClass(Class<?> targetClass) {
        return parser.isCandidateClass(targetClass);
    }

    protected boolean allowPublicMethodsOnly() {
        return publicMethodsOnly;
    }
}
