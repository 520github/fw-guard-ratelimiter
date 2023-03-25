package org.sunso.guard.ratelimiter;

import org.sunso.guard.ratelimiter.config.RateLimiterConfig;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Semaphore;

/**
 * 通过信号量控制某一个时刻的访问量
 */
public class SemaphoreRateLimiter implements GuardRateLimiter {
    Map<String, Semaphore> semaphoreMap = new ConcurrentHashMap<>();

    @Override
    public boolean acquire(RateLimiterConfig config) {
        Semaphore semaphore = getSemaphore(config);
        return semaphore.tryAcquire();
    }

    @Override
    public void release(RateLimiterConfig config, boolean acquire) {
        if (!acquire) {
            return;
        }
        Semaphore semaphore = getSemaphore(config);
        if (semaphore == null) {
            return;
        }
        semaphore.release();
    }

    private Semaphore getSemaphore(RateLimiterConfig config) {
        if (semaphoreMap.containsKey(config.getGroupKey())) {
            return semaphoreMap.get(config.getGroupKey());
        }
        synchronized (semaphoreMap) {
            if (semaphoreMap.get(config.getGroupKey()) == null) {
                semaphoreMap.put(config.getGroupKey(), new Semaphore(config.getLimitCount()));
            }
        }
        return semaphoreMap.get(config.getGroupKey());
    }
}
