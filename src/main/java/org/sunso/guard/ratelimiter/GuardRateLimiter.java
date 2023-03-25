package org.sunso.guard.ratelimiter;

import org.sunso.guard.ratelimiter.config.RateLimiterConfig;

public interface GuardRateLimiter {

    /**
     * 申请通行证
     * @param config
     * @return
     */
    boolean acquire(RateLimiterConfig config);

    /**
     * 释放通行证
     * @param config
     * @param acquire
     */
    void release(RateLimiterConfig config, boolean acquire);
}
