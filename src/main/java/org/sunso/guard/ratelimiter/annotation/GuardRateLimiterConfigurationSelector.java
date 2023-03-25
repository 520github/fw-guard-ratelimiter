package org.sunso.guard.ratelimiter.annotation;

import org.springframework.context.annotation.AdviceMode;
import org.springframework.context.annotation.AdviceModeImportSelector;
import org.springframework.context.annotation.AutoProxyRegistrar;

/**
 * 根据选择模式自动加载对应的配置类
 */
public class GuardRateLimiterConfigurationSelector extends AdviceModeImportSelector<EnableGuardRateLimiter> {
    @Override
    protected String[] selectImports(AdviceMode adviceMode) {
        switch (adviceMode) {
            case PROXY:
                return new String[]{AutoProxyRegistrar.class.getName(), ProxyGuardRateLimiterConfiguration.class.getName()};
            case ASPECTJ:
                return new String[]{AspectGuardRateLimiterConfiguration.class.getName()};
            default:
                return null;
        }
    }
}
