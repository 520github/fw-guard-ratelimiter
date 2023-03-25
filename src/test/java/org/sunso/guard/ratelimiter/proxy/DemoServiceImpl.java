package org.sunso.guard.ratelimiter.proxy;

public class DemoServiceImpl implements DemoService {
    @Override
    public DemoResult service() {
        return new DemoResult().setData("data");
    }

    public DemoResult fallbackService() {
        return new DemoResult().setData("fallback");
    }
}
