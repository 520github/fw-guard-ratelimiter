package org.sunso.guard.ratelimiter.interceptor;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.support.AopUtils;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.Serializable;

/**
 * 限流代理类的拦截器
 */
public class GuardRateLimiterInterceptor extends GuardRateLimiterAspectSupport implements MethodInterceptor, Serializable {
    @Nullable
    @Override
    public Object invoke(@Nonnull MethodInvocation invocation) throws Throwable {
        Class<?> targetClass = (invocation.getThis() != null ? AopUtils.getTargetClass(invocation.getThis()) : null);
        return invokeWithRateLimiter(invocation.getMethod(), targetClass, new InvocationCallback() {
            @Override
            public Object proceedWithInvocation() throws Throwable {
                return invocation.proceed();
            }

            @Override
            public Object getTarget() {
                return invocation.getThis();
            }

            @Override
            public Object[] getArgs() {
                return invocation.getArguments();
            }
        });
    }
}
