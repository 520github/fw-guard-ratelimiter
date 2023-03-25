package org.sunso.guard.ratelimiter.method;

import org.sunso.guard.ratelimiter.GuardRateLimiter;
import org.sunso.guard.ratelimiter.factory.GuardRateLimiterCacheFactory;
import org.sunso.guard.ratelimiter.config.MethodRateLimiterExecutorConfig;
import org.sunso.guard.ratelimiter.exception.RateLimiterException;

public abstract class AbstractMethodRateLimiterExecutor<R> implements MethodRateLimiterExecutor<R> {

    private MethodRateLimiterExecutorConfig config;

    public AbstractMethodRateLimiterExecutor(MethodRateLimiterExecutorConfig config) {
        this.config = config;
    }

    public R execute() {
        GuardRateLimiter rateLimiter = getGuardRateLimiter();
        boolean acquire = false;
        try {
            if (rateLimiter == null) {
                return doBizWithException();
            }
            acquire = rateLimiter.acquire(config.getConfig());
            if (!acquire) {
                return doFallback();
            }
            return doBizWithException();
        }catch (Exception e) {
            throw new RateLimiterException("call method in rate limiter found exception", e);
        }finally {
            if (rateLimiter != null) {
                rateLimiter.release(config.getConfig(), acquire);
            }
        }
    }

    protected R doBizWithException() {
        try {
            return doBiz();
        }catch (Throwable e) {
            return doFallback();
        }
    }

    protected abstract R doBiz() throws Throwable;

    protected abstract R doFallback();


    private GuardRateLimiter getGuardRateLimiter() {
        return GuardRateLimiterCacheFactory.getGuardRateLimiterByType(config.getRateLimiterType());
    }
}
