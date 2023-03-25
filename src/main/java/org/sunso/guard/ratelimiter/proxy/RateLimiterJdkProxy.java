package org.sunso.guard.ratelimiter.proxy;

import java.lang.reflect.Proxy;

public class RateLimiterJdkProxy {
    public static <I> I getProxy(Class c) throws Exception {
        return (I)Proxy.newProxyInstance(c.getClassLoader(), c.getInterfaces(), new MethodRateLimiterInvocationHandler(c.newInstance()));
    }

    public static <I> I getProxy(Object target, Class<I> targetInterface) {
        if (!targetInterface.isInterface()) {
            throw new IllegalStateException("targetInterface必须是接口");
        }
        if (!targetInterface.isAssignableFrom(target.getClass())) {
            throw new IllegalStateException("target必须实现"+ targetInterface+ "接口");
        }
        return (I)Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(), new MethodRateLimiterInvocationHandler(target));
    }
}
