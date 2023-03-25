package org.sunso.guard.ratelimiter.factory;

import org.sunso.guard.ratelimiter.GoogleRateLimiter;
import org.sunso.guard.ratelimiter.GuardRateLimiter;
import org.sunso.guard.ratelimiter.SemaphoreRateLimiter;
import org.sunso.guard.ratelimiter.annotation.RateLimiterType;
import org.sunso.guard.ratelimiter.helper.ApplicationContextHelper;

public class GuardRateLimiterFactory {

    /**
     * 根据限流类型获取对应限流对象
     * @param type
     * @return
     */
    public static GuardRateLimiter getGuardRateLimiterByType(RateLimiterType type) {
        if (RateLimiterType.SEMAPHORE == type) {
            return new SemaphoreRateLimiter();
        }
        else if (RateLimiterType.GOOGLE == type) {
            return new GoogleRateLimiter();
        }
        else if (RateLimiterType.REDIS == type) {
            return getRedisRateLimiter();
        }
        return null;
    }

    private static GuardRateLimiter getRedisRateLimiter() {
        return ApplicationContextHelper.getBean("redisRateLimiter", GuardRateLimiter.class);
    }
}
