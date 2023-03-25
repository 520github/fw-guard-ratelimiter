package org.sunso.guard.ratelimiter.interceptor;

import org.sunso.guard.ratelimiter.config.MethodRateLimiterExecutorConfig;
import org.sunso.guard.ratelimiter.exception.RateLimiterException;
import org.sunso.guard.ratelimiter.method.AbstractMethodRateLimiterExecutor;
import org.sunso.guard.ratelimiter.util.MethodUtil;

import java.lang.reflect.Method;

public abstract class GuardRateLimiterAspectSupport {
    private GuardRateLimiterAttributeSource guardRateLimiterAttributeSource;
    public void setGuardRateLimiterAttributeSource(GuardRateLimiterAttributeSource guardRateLimiterAttributeSource) {
        this.guardRateLimiterAttributeSource = guardRateLimiterAttributeSource;
    }

    /**
     * 增加限流逻辑的统一入口
     * @param method
     * @param targetClass
     * @param callback
     * @return
     * @throws Throwable
     */
    protected Object invokeWithRateLimiter(Method method, Class<?> targetClass, InvocationCallback callback) throws Throwable {
        GuardRateLimiterAttribute guardRateLimiterAttribute = guardRateLimiterAttributeSource.getGuardRateLimiterAttribute(method, targetClass);
        return new AbstractMethodRateLimiterExecutor<Object>(getMethodRateLimiterExecutorConfig(guardRateLimiterAttribute)) {
            @Override
            protected Object doBiz() throws Throwable {
                return callback.proceedWithInvocation();
            }
            @Override
            protected Object doFallback() {
                String fallbackMethod = guardRateLimiterAttribute.getFallbackMethod();
                if (fallbackMethod== null || fallbackMethod.length() < 1) {
                    throw new RateLimiterException("request speed exceeds rate limit");
                }
                Method fm = MethodUtil.getMethod(targetClass, fallbackMethod, method.getParameterTypes());
                if (fm == null) {
                    throw new RateLimiterException("request speed exceeds rate limit");
                }
                try {
                    return fm.invoke(callback.getTarget(), callback.getArgs());
                }catch (Exception e) {
                    throw new RateLimiterException("call fallback method exception when rate limiter happen ", e);
                }
            }
        }.execute();
    }

    protected interface InvocationCallback {
        Object proceedWithInvocation() throws Throwable;

        Object getTarget();

        Object[] getArgs();
    }

    private MethodRateLimiterExecutorConfig getMethodRateLimiterExecutorConfig(GuardRateLimiterAttribute attribute) {
        return MethodRateLimiterExecutorConfig.getMethodRateLimiterExecutorConfig(attribute);
    }
}
