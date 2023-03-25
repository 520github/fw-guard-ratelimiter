package org.sunso.guard.ratelimiter.interceptor;

import org.springframework.lang.Nullable;

import java.lang.reflect.Method;

public interface GuardRateLimiterAttributeSource {
    default boolean isCandidateClass(Class<?> targetClass) {
        return true;
    }

    @Nullable
    GuardRateLimiterAttribute getGuardRateLimiterAttribute(Method method, @Nullable Class<?> targetClass);
}
