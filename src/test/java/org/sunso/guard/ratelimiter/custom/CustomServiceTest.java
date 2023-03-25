package org.sunso.guard.ratelimiter.custom;

import org.junit.Assert;
import org.junit.Test;
import org.sunso.guard.ratelimiter.BastTest;

import java.util.concurrent.CountDownLatch;

public class CustomServiceTest extends BastTest {

    private CustomService customService = new CustomService();

    @Test
    public void getCustomDataSuccessTest() throws InterruptedException {
        String result = customService.getCustomData();
        Assert.assertEquals("success", result);
        print("result", result);
    }

    @Test
    public void getCustomDataSuccessAndFailTest() throws InterruptedException {
        int count = 1000;
        CountDownLatch countDownLatch = new CountDownLatch(count);
        for(int i=0; i<count; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    String result = null;
                    try {
                        result = customService.getCustomData();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    print("result", result);
                    countDownLatch.countDown();
                }
            }).start();
        }
        countDownLatch.await();
    }
}
