package org.sunso.guard.ratelimiter.annotation;

import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.annotation.AnnotationUtils;
import org.sunso.guard.ratelimiter.interceptor.DefaultGuardRateLimiterAttribute;
import org.sunso.guard.ratelimiter.interceptor.GuardRateLimiterAttribute;

import java.io.Serializable;
import java.lang.reflect.AnnotatedElement;

/**
 * @RateLimiter注解解析器的实现
 */
public class SpringGuardRateLimiterAnnotationParser implements GuardRateLimiterAnnotationParser, Serializable {

    @Override
    public boolean isCandidateClass(Class<?> targetClass) {
        return AnnotationUtils.isCandidateClass(targetClass, RateLimiter.class);
    }

    @Override
    public GuardRateLimiterAttribute parseGuardRateLimiterAnnotation(AnnotatedElement annotatedElement) {
        AnnotationAttributes attributes = AnnotatedElementUtils.findMergedAnnotationAttributes(annotatedElement, RateLimiter.class, false, false);
        return parseTransactionAnnotation(attributes);
    }

    protected GuardRateLimiterAttribute parseTransactionAnnotation(AnnotationAttributes attributes) {
        if (attributes == null) {
            return null;
        }
        DefaultGuardRateLimiterAttribute defaultAttribute = new DefaultGuardRateLimiterAttribute();
        defaultAttribute.setGroupKey(attributes.getString("groupKey"));
        defaultAttribute.setLimitCount(attributes.getNumber("limitCount"));
        defaultAttribute.setSecondTime(attributes.getNumber("secondTime"));
        defaultAttribute.setRateLimiterType(attributes.getEnum("rateLimiterType"));
        defaultAttribute.setFallbackMethod(attributes.getString("fallbackMethod"));
        return defaultAttribute;
    }
}
