package com.morak.performancetracker.aop.persistence;

import java.lang.reflect.Method;
import org.springframework.aop.support.StaticMethodMatcherPointcut;

public class ConnectionPointcut extends StaticMethodMatcherPointcut {

    private static final String METHOD_SUFFIX = "prepareStatement";

    @Override
    public boolean matches(Method method, Class<?> targetClass) {
        return method.getName().endsWith(METHOD_SUFFIX);
    }
}
