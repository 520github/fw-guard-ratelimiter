package org.sunso.guard.ratelimiter.factory;

import org.sunso.guard.ratelimiter.GuardRateLimiter;
import org.sunso.guard.ratelimiter.annotation.RateLimiterType;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class GuardRateLimiterCacheFactory {
    private static final Map<RateLimiterType, GuardRateLimiter> rateLimiterMap = new ConcurrentHashMap<>();

    public static GuardRateLimiter getGuardRateLimiterByType(RateLimiterType type) {
        if (rateLimiterMap.containsKey(type)) {
            return rateLimiterMap.get(type);
        }
        synchronized (rateLimiterMap) {
            if (rateLimiterMap.get(type) == null) {
                rateLimiterMap.put(type, GuardRateLimiterFactory.getGuardRateLimiterByType(type));
            }
        }
        return rateLimiterMap.get(type);
    }
}
