package org.sunso.guard.ratelimiter.config;

import org.sunso.guard.ratelimiter.annotation.RateLimiter;
import org.sunso.guard.ratelimiter.annotation.RateLimiterType;
import org.sunso.guard.ratelimiter.interceptor.GuardRateLimiterAttribute;

public class MethodRateLimiterExecutorConfig {
    /**
     * 限流配置
     */
    private RateLimiterConfig config;
    /**
     * 限流类型
     */
    private RateLimiterType rateLimiterType;

    /**
     * 限流触发后调用的方法
     */
    private String fallbackMethod;

    public static MethodRateLimiterExecutorConfig create() {
        return new MethodRateLimiterExecutorConfig();
    }

    public RateLimiterType getRateLimiterType() {
        return rateLimiterType;
    }

    public MethodRateLimiterExecutorConfig setRateLimiterType(RateLimiterType rateLimiterType) {
        this.rateLimiterType = rateLimiterType;
        return this;
    }

    public RateLimiterConfig getConfig() {
        return config;
    }

    public MethodRateLimiterExecutorConfig setConfig(RateLimiterConfig config) {
        this.config = config;
        return this;
    }

    public String getFallbackMethod() {
        return fallbackMethod;
    }

    public boolean isEmptyFallbackMethod() {
        if (fallbackMethod == null || fallbackMethod.length() < 1) {
            return true;
        }
        return false;
    }

    public MethodRateLimiterExecutorConfig setFallbackMethod(String fallbackMethod) {
        this.fallbackMethod = fallbackMethod;
        return this;
    }

    public static MethodRateLimiterExecutorConfig getMethodRateLimiterExecutorConfig(RateLimiter rateLimiter) {
        return MethodRateLimiterExecutorConfig.create()
                .setRateLimiterType(rateLimiter.rateLimiterType())
                .setFallbackMethod(rateLimiter.fallbackMethod())
                .setConfig(RateLimiterConfig.create()
                        .setGroupKey(rateLimiter.groupKey())
                        .setLimitCount(rateLimiter.limitCount())
                        .setSecondTime(rateLimiter.secondTime())
                );
    }

    public static MethodRateLimiterExecutorConfig getMethodRateLimiterExecutorConfig(GuardRateLimiterAttribute attribute) {
        return MethodRateLimiterExecutorConfig.create()
                .setRateLimiterType(attribute.getRateLimiterType())
                .setFallbackMethod(attribute.getFallbackMethod())
                .setConfig(RateLimiterConfig.create()
                        .setGroupKey(attribute.getGroupKey())
                        .setLimitCount(attribute.getLimitCount())
                        .setSecondTime(attribute.getSecondTime())
                );
    }
}
