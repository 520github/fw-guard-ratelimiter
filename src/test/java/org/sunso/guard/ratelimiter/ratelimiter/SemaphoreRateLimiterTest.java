package org.sunso.guard.ratelimiter.ratelimiter;

import org.junit.Assert;
import org.junit.Test;
import org.sunso.guard.ratelimiter.BastTest;
import org.sunso.guard.ratelimiter.SemaphoreRateLimiter;
import org.sunso.guard.ratelimiter.config.RateLimiterConfig;

public class SemaphoreRateLimiterTest extends BastTest {

    private SemaphoreRateLimiter limiter = new SemaphoreRateLimiter();

    @Test
    public void rateLimitPassTest() {
        boolean result = limiter.acquire(getRateLimiterConfig(5));
        Assert.assertTrue(result);
    }

    @Test
    public void rateLimitMultiPassTest() {
        RateLimiterConfig config = getRateLimiterConfig(5);
        for(int i=0; i<5; i++) {
            boolean result = limiter.acquire(config);
            Assert.assertTrue(result);
        }
        Assert.assertFalse(limiter.acquire(config));
    }

    @Test
    public void rateLimitRejectTest() {
        boolean result = limiter.acquire(getRateLimiterConfig(0));
        Assert.assertFalse(result);
    }

    @Test
    public void rateLimitMultiRejectTest() {
        RateLimiterConfig config = getRateLimiterConfig(1);
        Assert.assertTrue(limiter.acquire(config));
        for(int i=0; i<5; i++) {
            boolean result = limiter.acquire(config);
            Assert.assertFalse(result);
        }
    }

    @Test
    public void rateLimitOneGroupKeyRejectAndOneGroupKeyPassTest() {
        Assert.assertFalse(limiter.acquire(getRateLimiterConfig(0)));
        int limitCount = 3;
        RateLimiterConfig config = getOtherRateLimiterConfig(limitCount);
        for(int i=0; i<5; i++) {
            boolean result = limiter.acquire(config);
            if (i <limitCount) {
                Assert.assertTrue(result);
            }else {
                Assert.assertFalse(result);
            }
        }
    }

    @Test
    public void rateLimitReleaseTest() {
        int limitCount = 3;
        RateLimiterConfig config = getOtherRateLimiterConfig(limitCount);
        int successCount = 0;
        for(int i=0; i<5; i++) {
            boolean result = limiter.acquire(config);
            if (result) successCount++;
            if (i <limitCount) {
                Assert.assertTrue(result);
            }else {
                Assert.assertFalse(result);
            }
        }
        for(int i=0; i<successCount; i++) {
            limiter.release(config, true);
        }
        for(int i=0; i<limitCount; i++) {
            Assert.assertTrue(limiter.acquire(config));
        }
    }

    protected RateLimiterConfig getRateLimiterConfig(int limitCount) {
        return getRateLimiterConfig("semaphore:one", limitCount, 0);
    }

    protected RateLimiterConfig getOtherRateLimiterConfig(int limitCount) {
        return getRateLimiterConfig("semaphore:two", limitCount, 0);
    }
}
