package org.sunso.guard.ratelimiter.proxy;

import org.junit.Test;
import org.sunso.guard.ratelimiter.BastTest;

public class DemoServiceImplTest extends BastTest {

    @Test
    public void demoServiceTest() throws Exception {
        DemoService demoService = RateLimiterJdkProxy.getProxy(DemoServiceImpl.class);
        DemoResult demoResult = demoService.service();
        print("data" ,demoResult.getData());
    }
}
