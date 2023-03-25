package org.sunso.guard.ratelimiter.custom;

import org.sunso.guard.ratelimiter.GuardRateLimiter;
import org.sunso.guard.ratelimiter.SemaphoreRateLimiter;
import org.sunso.guard.ratelimiter.config.RateLimiterConfig;

public class CustomService {
    //通过信号量方式限流
    private final static GuardRateLimiter rateLimiter = new SemaphoreRateLimiter();
    //方法限流参数配置
    public final static RateLimiterConfig config = RateLimiterConfig.create()
            .setGroupKey("rate:limiter:custom:service")
            .setLimitCount(20)
            ;

    public String getCustomData() throws InterruptedException {
        boolean acquire = false;
        try {
            acquire = rateLimiter.acquire(config);
            Thread.sleep(1);
            if(!acquire) {
                return "fail";
            }
            return "success";
        }finally {
            //获取信号量成功后，需要释放信号量
            rateLimiter.release(config, acquire);
        }
    }
}
