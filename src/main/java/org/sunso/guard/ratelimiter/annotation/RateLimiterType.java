package org.sunso.guard.ratelimiter.annotation;

public enum RateLimiterType {
    SEMAPHORE,//信号量，本地并发量限流
    REDIS,//redis分布式限流
    GOOGLE //guava的RateLimiter限流
    ;

    RateLimiterType() {
    }
}
