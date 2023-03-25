package org.sunso.guard.ratelimiter.method;

import org.sunso.guard.ratelimiter.annotation.RateLimiterType;
import org.sunso.guard.ratelimiter.config.MethodRateLimiterExecutorConfig;
import org.sunso.guard.ratelimiter.config.RateLimiterConfig;

public class SemaphoreMethodRateLimiterExecutor extends AbstractMethodRateLimiterExecutor<String> {

    public SemaphoreMethodRateLimiterExecutor() {
        super(MethodRateLimiterExecutorConfig.create()
                .setRateLimiterType(RateLimiterType.SEMAPHORE)
                .setConfig(RateLimiterConfig.create()
                        .setGroupKey("SemaphoreTest")
                        .setLimitCount(10)
                        .setSecondTime(1)));
    }
    @Override
    protected String doBiz() {//具体执行的业务代码
        return "success";
    }

    @Override
    protected String doFallback() {//限流触发后执行的代码
        return "fallback";
    }
}
