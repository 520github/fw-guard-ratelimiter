package org.sunso.guard.ratelimiter;

import org.springframework.data.redis.core.RedisTemplate;
import org.sunso.guard.ratelimiter.config.RateLimiterConfig;

import java.util.concurrent.TimeUnit;


/**
 * 基于redis控制某一个时间间隔的访问量
 */
public class RedisRateLimiter implements GuardRateLimiter {
    private RedisTemplate redisTemplate;

    public void setRedisTemplate(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public boolean acquire(RateLimiterConfig config) {
        checkRedisClient();
        long count = increment(config);
        if (count > config.getLimitCount()) {
            return false;
        }
        return true;
    }

    private long increment(RateLimiterConfig config) {
        if (redisTemplate != null) {
            redisTemplate.opsForValue().setIfAbsent(config.getGroupKey(), "0", config.getSecondTime(), TimeUnit.SECONDS);
            return redisTemplate.opsForValue().increment(config.getGroupKey());
        }
        return Long.MAX_VALUE;
    }

    @Override
    public void release(RateLimiterConfig config, boolean acquire) {
        if (!acquire) {
            return;
        }
        checkRedisClient();
        doRelease(config);
    }

    private void doRelease(RateLimiterConfig config) {
        if (redisTemplate != null && redisTemplate.hasKey(config.getGroupKey())) {
            long result = redisTemplate.opsForValue().decrement(config.getGroupKey());
            if (result < 0) {
                redisTemplate.expire(config.getGroupKey(), config.getSecondTime(), TimeUnit.SECONDS);
            }
        }
    }

    private void checkRedisClient() {
        if (redisTemplate == null) {
            throw new IllegalStateException("redisTemplate is must be set");
        }
    }
}
