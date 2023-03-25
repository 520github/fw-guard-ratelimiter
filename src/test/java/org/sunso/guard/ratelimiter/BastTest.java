package org.sunso.guard.ratelimiter;

import org.sunso.guard.ratelimiter.config.RateLimiterConfig;

public abstract class BastTest {
    protected void print(String title, Object value) {
        System.out.println(title + "->" + value);
    }

    protected RateLimiterConfig getRateLimiterConfig(String groupKey, int limitCount, int secondTime) {
        return RateLimiterConfig.create()
                .setGroupKey(groupKey)
                .setLimitCount(limitCount)
                .setSecondTime(secondTime);
    }
}
