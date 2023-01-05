package com.morak.performancetracker.aop.rest;

import java.lang.reflect.Method;
import org.springframework.aop.support.StaticMethodMatcherPointcut;

public class RestPointcut extends StaticMethodMatcherPointcut {

    @Override
    public boolean matches(Method method, Class<?> ignored) {
        return method.getName().contains("execute");
    }
}
