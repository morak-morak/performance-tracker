package com.morak.performancetracker.aop;

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
        if (isExecute(method)) {
            return measureQueryPerformance(method, args);
        }
        return method.invoke(preparedStatement, args);
    }

    private boolean isExecute(Method method) {
        return method.getName().contains(EXECUTION_PREFIX);
    }

    private Object measureQueryPerformance(Method method, Object[] args) throws Throwable {
        long startTime = System.nanoTime();
        Object returnValue = method.invoke(preparedStatement, args);
        performanceMonitor.addQueryTime(System.nanoTime() - startTime);
        performanceMonitor.increaseQueryCount();
        return returnValue;
    }
}
