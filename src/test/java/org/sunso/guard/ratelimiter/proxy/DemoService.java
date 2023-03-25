package org.sunso.guard.ratelimiter.proxy;

import org.sunso.guard.ratelimiter.annotation.RateLimiter;

public interface DemoService {

    @RateLimiter(limitCount = 2,fallbackMethod = "fallbackService")
    public DemoResult service();
}
