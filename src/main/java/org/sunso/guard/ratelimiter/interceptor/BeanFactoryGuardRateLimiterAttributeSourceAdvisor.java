package org.sunso.guard.ratelimiter.interceptor;

import org.springframework.aop.Pointcut;
import org.springframework.aop.support.AbstractBeanFactoryPointcutAdvisor;

/**
 * 定义代理的Advisor
 */
public class BeanFactoryGuardRateLimiterAttributeSourceAdvisor extends AbstractBeanFactoryPointcutAdvisor {

    private GuardRateLimiterAttributeSource guardRateLimiterAttributeSource;

    private final GuardRateLimiterAttributeSourcePointcut pointcut = new GuardRateLimiterAttributeSourcePointcut(){
        @Override
        protected GuardRateLimiterAttributeSource getGuardRateLimiterAttributeSource() {
            return BeanFactoryGuardRateLimiterAttributeSourceAdvisor.this.guardRateLimiterAttributeSource;
        }
    };

    public void setGuardRateLimiterAttributeSource(GuardRateLimiterAttributeSource guardRateLimiterAttributeSource) {
        this.guardRateLimiterAttributeSource = guardRateLimiterAttributeSource;
    }


    @Override
    public Pointcut getPointcut() {
        return this.pointcut;
    }
}
