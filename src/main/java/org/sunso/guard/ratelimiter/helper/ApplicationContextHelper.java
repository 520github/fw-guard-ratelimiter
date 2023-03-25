package org.sunso.guard.ratelimiter.helper;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * 根据bean name或class获取对应bean对象
 */
@Component("rateLimiterApplicationContextHelper")
@Slf4j
public class ApplicationContextHelper implements ApplicationContextAware {
    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ApplicationContextHelper.applicationContext = applicationContext;
    }

    public static <T> T getBean(Class<T> targetClz) {
        T beanInstance = null;
        // 优先按type查
        try {
            beanInstance = (T) applicationContext.getBean(targetClz);
        } catch (Exception e) {
        }

        // 按name查
        try {
            if (beanInstance == null) {
                String simpleName = targetClz.getSimpleName();
                // 首字母小写
                simpleName = Character.toLowerCase(simpleName.charAt(0)) + simpleName.substring(1);
                beanInstance = (T) applicationContext.getBean(simpleName);
            }
        } catch (Exception e) {
            log.warn("No bean found for " + targetClz.getCanonicalName());
        }
        return beanInstance;
    }

    public static Object getBean(String claz) {
        return ApplicationContextHelper.applicationContext.getBean(claz);
    }

    public static <T> T getBean(String name, Class<T> requiredType) {
        return ApplicationContextHelper.applicationContext.getBean(name, requiredType);
    }

    public static <T> T getBean(Class<T> requiredType, Object... params) {
        return ApplicationContextHelper.applicationContext.getBean(requiredType, params);
    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }
}
