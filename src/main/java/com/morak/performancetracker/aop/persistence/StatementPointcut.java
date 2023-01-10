package com.morak.performancetracker.aop.persistence;

import java.lang.reflect.Method;
import org.springframework.aop.support.StaticMethodMatcherPointcut;

public class StatementPointcut extends StaticMethodMatcherPointcut {

    private static final String EXECUTION_PREFIX = "execute";

    @Override
    public boolean matches(Method method, Class<?> targetClass) {
        return method.getName().contains(EXECUTION_PREFIX);
    }
}
