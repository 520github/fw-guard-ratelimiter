package org.sunso.guard.ratelimiter.annotation;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Role;
import org.sunso.guard.ratelimiter.interceptor.BeanFactoryGuardRateLimiterAttributeSourceAdvisor;
import org.sunso.guard.ratelimiter.interceptor.GuardRateLimiterAttributeSource;
import org.sunso.guard.ratelimiter.interceptor.GuardRateLimiterInterceptor;

/**
 * 代理方式配置类
 */
@Configuration (
        proxyBeanMethods = false
)
@Role(2)
public class ProxyGuardRateLimiterConfiguration extends AbstractGuardRateLimiterConfiguration {
    public ProxyGuardRateLimiterConfiguration() {
    }

    @Bean
    @Role(2)
    public BeanFactoryGuardRateLimiterAttributeSourceAdvisor guardRateLimiterAttributeSourceAdvisor(GuardRateLimiterAttributeSource guardRateLimiterAttributeSource, GuardRateLimiterInterceptor guardRateLimiterInterceptor) {
        BeanFactoryGuardRateLimiterAttributeSourceAdvisor advisor = new BeanFactoryGuardRateLimiterAttributeSourceAdvisor();
        advisor.setGuardRateLimiterAttributeSource(guardRateLimiterAttributeSource);
        advisor.setAdvice(guardRateLimiterInterceptor);
        return advisor;
    }

    @Bean
    @Role(2)
    public GuardRateLimiterAttributeSource guardRateLimiterAttributeSource() {
        return new AnnotationGuardRateLimiterAttributeSource();
    }

    @Bean
    @Role(2)
    public GuardRateLimiterInterceptor guardRateLimiterInterceptor(GuardRateLimiterAttributeSource guardRateLimiterAttributeSource) {
        GuardRateLimiterInterceptor guardRateLimiterInterceptor = new GuardRateLimiterInterceptor();
        guardRateLimiterInterceptor.setGuardRateLimiterAttributeSource(guardRateLimiterAttributeSource);
        return guardRateLimiterInterceptor;
    }
}
