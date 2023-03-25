package org.sunso.guard.ratelimiter.interceptor;

import org.springframework.lang.Nullable;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public abstract class AbstractGuardRateLimiterAttributeSource implements GuardRateLimiterAttributeSource{
    @Override
    public GuardRateLimiterAttribute getGuardRateLimiterAttribute(Method method, Class<?> targetClass) {
        if (method.getDeclaringClass() == Object.class) {
            return null;
        }
        return computeGuardRateLimiterAttribute(method, targetClass);
    }

    protected GuardRateLimiterAttribute computeGuardRateLimiterAttribute(Method method, Class<?> targetClass) {
        if (this.allowPublicMethodsOnly() && !Modifier.isPublic(method.getModifiers())) {
            return null;
        }
        return findGuardRateLimiterAttribute(method);
    }

    @Nullable
    protected abstract GuardRateLimiterAttribute findGuardRateLimiterAttribute(Method method);

    protected boolean allowPublicMethodsOnly() {
        return false;
    }
}
