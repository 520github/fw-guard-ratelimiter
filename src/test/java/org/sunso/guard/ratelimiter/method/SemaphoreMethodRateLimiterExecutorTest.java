package org.sunso.guard.ratelimiter.method;

import org.junit.Test;
import org.sunso.guard.ratelimiter.BastTest;

public class SemaphoreMethodRateLimiterExecutorTest extends BastTest {
    private SemaphoreMethodRateLimiterExecutor executor = new SemaphoreMethodRateLimiterExecutor();

    @Test
    public void executeTest() {
        String result = executor.execute();
        print("result", result);
    }
}
