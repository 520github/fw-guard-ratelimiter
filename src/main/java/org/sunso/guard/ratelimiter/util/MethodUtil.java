package org.sunso.guard.ratelimiter.util;

import java.lang.reflect.Method;

public class MethodUtil {

    public static Method getMethod(Class<?> type, String name,  Class<?>... parameterTypes) {
        Method[] methods = type.getDeclaredMethods();
        for(int i=0; i< methods.length; i++) {
            Method method = methods[i];
            if (method.getName().equals(name)) {
                return method;
            }
        }
        Class<?> superClass = type.getSuperclass();
        if (superClass == null || superClass.equals(Object.class)) {
            return null;
        }
        return getMethod(superClass, name, parameterTypes);
    }
}
