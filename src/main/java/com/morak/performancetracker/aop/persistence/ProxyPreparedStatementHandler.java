package com.morak.performancetracker.aop.persistence;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class ProxyPreparedStatementHandler implements InvocationHandler {

    private static final String EXECUTION_PREFIX = "execute";
    private final Object preparedStatement;
    private final PerformanceMonitor performanceMonitor;

    public ProxyPreparedStatementHandler(Object preparedStatement, PerformanceMonitor performanceMonitor) {
        this.preparedStatement = preparedStatement;
        this.performanceMonitor = performanceMonitor;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (!isExecution(method)) {
            return method.invoke(preparedStatement, args);
        }
        return measureQueryPerformance(method, args);
    }

    private boolean isExecution(Method method) {
        return method.getName().contains(EXECUTION_PREFIX);
    }

    private Object measureQueryPerformance(Method method, Object[] args) throws Throwable {
        performanceMonitor.start();
        Object returnValue = method.invoke(preparedStatement, args);
        performanceMonitor.end();
        return returnValue;
    }
}
