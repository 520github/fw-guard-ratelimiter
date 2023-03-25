package org.sunso.guard.ratelimiter.annotation;

import java.lang.annotation.*;

/**
 * 限流的注解
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RateLimiter {

    /**
     * 限流分组key
     * @return
     */
    String groupKey() default "rate:limiter";

    /**
     * 限流次数
     * @return
     */
    int limitCount() default  10;

    /**
     * 限流时间范围(单位秒）
     * @return
     */
    int secondTime() default 1;

    /**
     * 限流类型
     * @return
     */
    RateLimiterType rateLimiterType() default RateLimiterType.SEMAPHORE;

    /**
     * 限流触发后的回调方法
     * @return
     */
    String fallbackMethod() default "";
}
