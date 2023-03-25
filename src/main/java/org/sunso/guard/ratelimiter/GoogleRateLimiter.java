package org.sunso.guard.ratelimiter;

import com.google.common.util.concurrent.RateLimiter;
import org.sunso.guard.ratelimiter.config.RateLimiterConfig;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 通过令牌桶控制某一个时间间隔的访问量
 */
public class GoogleRateLimiter implements GuardRateLimiter {
    Map<String, RateLimiter> rateLimiterMap = new ConcurrentHashMap<>();

    @Override
    public boolean acquire(RateLimiterConfig config) {
        return getRateLimiter(config).tryAcquire();
    }

    @Override
    public void release(RateLimiterConfig config, boolean acquire) {

    }

    private RateLimiter getRateLimiter(RateLimiterConfig config) {
        if (rateLimiterMap.containsKey(config.getGroupKey())) {
            return rateLimiterMap.get(config.getGroupKey());
        }
        synchronized (rateLimiterMap) {
            if (rateLimiterMap.get(config.getGroupKey()) == null) {
                rateLimiterMap.put(config.getGroupKey(), RateLimiter.create(config.getLimitCount()));
            }
        }
        return rateLimiterMap.get(config.getGroupKey());
    }
}
