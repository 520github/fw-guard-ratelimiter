package org.sunso.guard.ratelimiter.proxy;

import org.sunso.guard.ratelimiter.annotation.AnnotationGuardRateLimiterAttributeSource;
import org.sunso.guard.ratelimiter.annotation.RateLimiter;
import org.sunso.guard.ratelimiter.interceptor.GuardRateLimiterAspectSupport;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class MethodRateLimiterInvocationHandler extends GuardRateLimiterAspectSupport implements InvocationHandler {
    Object target;

    public MethodRateLimiterInvocationHandler(Object target) {
        this.target = target;
        setGuardRateLimiterAttributeSource(new AnnotationGuardRateLimiterAttributeSource());
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        RateLimiter rateLimiter = method.getAnnotation(RateLimiter.class);
        if (rateLimiter == null) {
            return method.invoke(target, args);
        }
        return invokeWithRateLimiter(method, target.getClass(), new InvocationCallback() {
            @Override
            public Object proceedWithInvocation() throws Throwable {
                return method.invoke(target, args);
            }

            @Override
            public Object getTarget() {
                return target;
            }

            @Override
            public Object[] getArgs() {
                return args;
            }
        });
    }
}
