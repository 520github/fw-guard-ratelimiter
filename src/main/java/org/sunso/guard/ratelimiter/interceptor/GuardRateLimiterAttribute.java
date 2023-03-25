package org.sunso.guard.ratelimiter.interceptor;

import org.sunso.guard.ratelimiter.annotation.RateLimiterType;

/**
 * 限流属性
 */
public interface GuardRateLimiterAttribute {
    default String getGroupKey() {
        return "rate:limiter";
    }

    default int getLimitCount() {
        return 10;
    }

    default int getSecondTime() {
        return 1;
    }

    default RateLimiterType getRateLimiterType() {
        return RateLimiterType.SEMAPHORE;
    }

    default String getFallbackMethod() {
        return null;
    }
}
