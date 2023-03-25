package org.sunso.guard.ratelimiter.method;

public interface MethodRateLimiterExecutor<R> {
    R execute();
}
