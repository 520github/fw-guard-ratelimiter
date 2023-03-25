package org.sunso.guard.ratelimiter.interceptor;

import org.springframework.aop.support.StaticMethodMatcherPointcut;

import java.io.Serializable;
import java.lang.reflect.Method;

public abstract class GuardRateLimiterAttributeSourcePointcut extends StaticMethodMatcherPointcut implements Serializable {

    @Override
    public boolean matches(Method method, Class<?> targetClass) {
        GuardRateLimiterAttributeSource gas = getGuardRateLimiterAttributeSource();
        return gas == null || gas.getGuardRateLimiterAttribute(method, targetClass) != null;
    }

    protected abstract GuardRateLimiterAttributeSource getGuardRateLimiterAttributeSource();
}
