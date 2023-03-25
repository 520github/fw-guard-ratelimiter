package org.sunso.guard.ratelimiter.annotation;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportAware;
import org.springframework.context.annotation.Role;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.lang.Nullable;
import org.sunso.guard.ratelimiter.RedisRateLimiter;
import org.sunso.guard.ratelimiter.helper.ApplicationContextHelper;

@Configuration
public abstract class AbstractGuardRateLimiterConfiguration implements ImportAware {
    @Nullable
    protected AnnotationAttributes enableRateLimit;

    @Override
    public void setImportMetadata(AnnotationMetadata importMetadata) {
        this.enableRateLimit = AnnotationAttributes.fromMap(importMetadata.getAnnotationAttributes(EnableGuardRateLimiter.class.getName(), false));
        if (this.enableRateLimit == null) {
            throw new IllegalArgumentException("@EnableGuardRateLimiter is not present on importing class " + importMetadata.getClassName());
        }
    }

    @Bean
    @Role(2)
    public RedisRateLimiter redisRateLimiter(RedisTemplate rateLimiterRedisTemplate) {
        RedisRateLimiter redisRateLimiter = new RedisRateLimiter();
        redisRateLimiter.setRedisTemplate(rateLimiterRedisTemplate);
        return redisRateLimiter;
    }

    @Bean("rateLimiterApplicationContextHelper")
    public ApplicationContextHelper applicationContextHelper() {
        return new ApplicationContextHelper();
    }
}
