package org.sunso.guard.ratelimiter.interceptor;

import org.sunso.guard.ratelimiter.annotation.RateLimiterType;

public class DefaultGuardRateLimiterAttribute implements GuardRateLimiterAttribute {
    /**
     * 限流分组key (不同组key之间相互隔离)
     */
    private String groupKey;
    /**
     * 限流次数
     */
    private int limitCount;
    /**
     * 限流时间范围(单位秒)
     */
    private int secondTime;
    /**
     * 限流类型
     */
    private RateLimiterType rateLimiterType;
    /**
     * 限流触发后调用的方法
     */
    private String fallbackMethod;

    @Override
    public String getGroupKey() {
        return groupKey;
    }

    @Override
    public int getLimitCount() {
        return limitCount;
    }

    @Override
    public int getSecondTime() {
        return secondTime;
    }

    @Override
    public RateLimiterType getRateLimiterType() {
        return rateLimiterType;
    }

    public DefaultGuardRateLimiterAttribute setGroupKey(String groupKey) {
        this.groupKey = groupKey;
        return this;
    }

    public DefaultGuardRateLimiterAttribute setLimitCount(int limitCount) {
        this.limitCount = limitCount;
        return this;
    }

    public DefaultGuardRateLimiterAttribute setSecondTime(int secondTime) {
        this.secondTime = secondTime;
        return this;
    }

    public DefaultGuardRateLimiterAttribute setRateLimiterType(RateLimiterType rateLimiterType) {
        this.rateLimiterType = rateLimiterType;
        return this;
    }

    @Override
    public String getFallbackMethod() {
        return fallbackMethod;
    }

    public DefaultGuardRateLimiterAttribute setFallbackMethod(String fallbackMethod) {
        this.fallbackMethod = fallbackMethod;
        return this;
    }
}
