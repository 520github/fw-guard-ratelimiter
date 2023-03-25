简单、灵活、支持多种方式的java本地、分布式的限流框架
======================

#### 限流的背景
* 服务系统或依赖的服务系统，短时间内只能处理一定数量的请求，当请求量严重超过系统处理范围时，就会造成大量堆积压垮服务，而导致服务不可用。
* 所以为了避免这类问题的发生，我们为接口或方法添加限流、降级、熔断等能力，从而让服务更健壮、更可靠。
* 在接口或方法上控制，在N秒内最多能同时并发处理M次请求。


#### 限流支持方式

| 概念                  | 说明                          | 对应实现类                                         |
|---------------------|-----------------------------|-----------------------------------------------|
| semaphore信号量限流      | 本地限流,不需要指定时间，主要控制并发量        | org.sunso.guard.ratelimiter.SemaphoreRateLimiter|
| guava的RateLimiter限流 | 本地限流，每秒会生成固定数量令牌，令牌消耗完，请求失败 | org.sunso.guard.ratelimiter.GoogleRateLimiter |
| redis限流             | 分布式限流，在指定时间内限制并发处理数         | org.sunso.guard.ratelimiter.RedisRateLimiter  |


#### 限流集成方式
* 业务代码直接集成限流
* 继承AbstractMethodRateLimiterExecutor集成限流
* 通过代理和@RateLimiter注解集成限流
* 通过springboot aop方式集成限流
* 通过springboot proxy方式集成限流


#### Quick Start
###### 业务代码直接集成限流--semaphore信号量限流方式
~~~
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
~~~

###### 继承AbstractMethodRateLimiterExecutor集成限流
~~~
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

//方法调用
new SemaphoreMethodRateLimiterExecutor().execute();
~~~

###### 通过代理和@RateLimiter注解集成限流
~~~
-- 定义结果对象
package org.sunso.guard.ratelimiter.proxy;

public class DemoResult {
    private String data;

    public String getData() {
        return data;
    }

    public DemoResult setData(String data) {
        this.data = data;
        return this;
    }
}

--定义接口，接口方法设置限流注解
package org.sunso.guard.ratelimiter.proxy;

import org.sunso.guard.ratelimiter.annotation.RateLimiter;

public interface DemoService {

    @RateLimiter(limitCount = 2,fallbackMethod = "fallbackService")
    public DemoResult service();
}

--实现接口
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

-- 通过代理方法调用
DemoService demoService = RateLimiterJdkProxy.getProxy(DemoServiceImpl.class);
DemoResult demoResult = demoService.service();
~~~

###### 通过springboot proxy方式集成限流
~~~
--1 使用redis做分布式限流方式，需要配置redisTempalte
@Bean("rateLimiterRedisTemplate")
    public RedisTemplate<String, Object> rateLimiterRedisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(factory);

        GenericToStringSerializer genericToStringSerializer = new GenericToStringSerializer(Object.class);
        template.setValueSerializer(genericToStringSerializer);

        template.setKeySerializer(new StringRedisSerializer());
        template.afterPropertiesSet();
        return template;
    }
    
--2 接口或方法上增加@RateLimiter注解 
@RestController
public class RateLimiterController {

    @ResponseBody
    @RequestMapping("/rate/yes")
    @RateLimiter(groupKey = "rate:limit:test:1" ,limitCount = 1, secondTime = 10, rateLimiterType = RateLimiterType.REDIS, fallbackMethod = "fallbackMethod")
    public  String rateLimiter(String name) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return name+"-ok";
    }

    public String fallbackMethod(String name) {
        return  name+"-fallback";
    }
}
    
--3 开启springboot限流配置--@EnableGuardRateLimiter()

@SpringBootApplication() 
@EnableGuardRateLimiter()
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}    
~~~

###### 通过springboot aop方式集成限流
~~~
与springboot proxy方式集成一致，区别在于@EnableGuardRateLimiter()模式的设置

@EnableGuardRateLimiter(mode = AdviceMode.ASPECTJ)
~~~