package org.sunso.guard.ratelimiter.config;

public class RateLimiterConfig {
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

    public static RateLimiterConfig create() {
        return new RateLimiterConfig();
    }

    public String getGroupKey() {
        return groupKey;
    }

    public RateLimiterConfig setGroupKey(String groupKey) {
        this.groupKey = groupKey;
        return this;
    }

    public int getLimitCount() {
        return limitCount;
    }

    public RateLimiterConfig setLimitCount(int limitCount) {
        this.limitCount = limitCount;
        return this;
    }

    public int getSecondTime() {
        return secondTime;
    }

    public RateLimiterConfig setSecondTime(int secondTime) {
        this.secondTime = secondTime;
        return this;
    }
}
