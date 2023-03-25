package org.sunso.guard.ratelimiter.annotation;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Role;
import org.sunso.guard.ratelimiter.aspect.AnnotationRateLimiterAspect;

/**
 * AOP方式配置类
 */
@Configuration
@Role(2)
public class AspectGuardRateLimiterConfiguration extends AbstractGuardRateLimiterConfiguration {
    public AspectGuardRateLimiterConfiguration() {
    }

    @Bean
    @Role(2)
    public AnnotationRateLimiterAspect annotationRateLimiterAspect() {
        return new AnnotationRateLimiterAspect();
    }
}
