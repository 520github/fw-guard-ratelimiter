package org.sunso.guard.ratelimiter.ratelimiter;

import org.junit.Assert;
import org.junit.Test;
import org.sunso.guard.ratelimiter.BastTest;
import org.sunso.guard.ratelimiter.GoogleRateLimiter;
import org.sunso.guard.ratelimiter.config.RateLimiterConfig;

import java.util.concurrent.atomic.AtomicInteger;

public class GoogleRateLimiterTest extends BastTest {
    private GoogleRateLimiter limiter = new GoogleRateLimiter();


    @Test
    public void rateLimitPassTest() {
        boolean result = limiter.acquire(getRateLimiterConfig(5));
        Assert.assertTrue(result);
    }

    @Test
    public void rateLimitMultiPassTest() throws InterruptedException {
        int limitCount = 5;
        int sleepTime = 1000;
        int sleepNum = 2;
        //每秒往令牌桶仍5个令牌
        RateLimiterConfig config = initRateLimit(limitCount);
        for(int j=0;j<sleepNum; j++) {
            for(int i=0; i<limitCount; i++) {
                Assert.assertTrue(limiter.acquire(config));
            }
            if (j<sleepNum-1) {
                Thread.sleep(sleepTime);
            }
        }
        Assert.assertFalse(limiter.acquire(config));
    }

    @Test
    public void rateLimitPassAndRejectTest() throws InterruptedException {
        int limitCount = 5;
        //每秒往令牌桶仍5个令牌
        RateLimiterConfig config = initRateLimit(limitCount);
        AtomicInteger execNum = new AtomicInteger();
        int threadNum = 20;
        for(int i=0; i<threadNum; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    if (execNum.incrementAndGet() > limitCount) {
                        Assert.assertFalse(limiter.acquire(config));
                    }
                    else {
                        Assert.assertTrue(limiter.acquire(config));
                    }
                }
            }).start();;
        }
    }

    @Test
    public void rateLimitTwoGroupKeyTest() throws InterruptedException {
        int limitCount = 5;
        //每秒往令牌桶仍5个令牌
        RateLimiterConfig config = initRateLimit(limitCount);
        long begin = System.currentTimeMillis();
        for(int i=0; i<limitCount+Math.random()*20; i++) {
            if (i<limitCount) {
                Assert.assertTrue(limiter.acquire(config));
            }
            else {
                Assert.assertFalse(limiter.acquire(config));
            }
        }
        print("first useTime", System.currentTimeMillis() - begin);

        RateLimiterConfig otherConfig = initOtherRateLimit(limitCount);
        begin = System.currentTimeMillis();
        for(int i=0; i<limitCount+Math.random()*20; i++) {
            if (i<limitCount) {
                Assert.assertTrue(limiter.acquire(otherConfig));
            }
            else {
                Assert.assertFalse(limiter.acquire(otherConfig));
            }
        }
        print("two useTime", System.currentTimeMillis() - begin);
        //睡眠时间太短，令牌还没有产生
        Thread.sleep(100);
        Assert.assertFalse(limiter.acquire(otherConfig));

        //睡眠一秒后，继续产生令牌，可以使用
        Thread.sleep(1000);
        Assert.assertTrue(limiter.acquire(config));
        Assert.assertTrue(limiter.acquire(otherConfig));
    }

    @Test
    public void rateLimitProducerAndConsumerTest() throws InterruptedException {
        int limitCount = 5;
        //生产令牌
        RateLimiterConfig config = initRateLimit(limitCount);
        //消费令牌
        for(int i=0;i<2;i++) {
            Assert.assertTrue(limiter.acquire(config));
        }
        //补充令牌
        Thread.sleep(1000);
        //再消费令牌
        for(int i=0; i<6; i++) {
            Assert.assertTrue(limiter.acquire(config));
        }
        //再补充令牌
        Thread.sleep(1000);
        //消费所有令牌
        for(int i=0; i<limitCount; i++) {
            Assert.assertTrue(limiter.acquire(config));
        }
        //预期结果没有令牌可用
        Assert.assertFalse(limiter.acquire(config));
    }

    private RateLimiterConfig initRateLimit(int limitCount) throws InterruptedException {
        //每秒往令牌桶仍5个令牌
        RateLimiterConfig config = getRateLimiterConfig(limitCount);
        //init
        Assert.assertTrue(limiter.acquire(config));
        Thread.sleep(1000);
        return config;
    }
    private RateLimiterConfig initOtherRateLimit(int limitCount) throws InterruptedException {
        //每秒往令牌桶仍5个令牌
        RateLimiterConfig config = getOtherRateLimiterConfig(limitCount);
        //init
        Assert.assertTrue(limiter.acquire(config));
        Thread.sleep(1000);
        return config;
    }

    protected RateLimiterConfig getRateLimiterConfig(int limitCount) {
        return getRateLimiterConfig("google:one", limitCount, 0);
    }

    protected RateLimiterConfig getOtherRateLimiterConfig(int limitCount) {
        return getRateLimiterConfig("google:two", limitCount, 0);
    }
}
