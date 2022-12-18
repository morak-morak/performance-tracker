package com.morak.performancetracker.aop.persistence;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class ProxyConnectionHandler implements InvocationHandler {

    private static final String METHOD_SUFFIX = "Statement";

    private final Object connection;
    private final PerformanceMonitor performanceMonitor;

    public ProxyConnectionHandler(Object connection, PerformanceMonitor performanceMonitor) {
        this.connection = connection;
        this.performanceMonitor = performanceMonitor;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object returnValue = method.invoke(connection, args);
        if (isPrepareStatement(method)) {
            return Proxy.newProxyInstance(
                    returnValue.getClass().getClassLoader(),
                    returnValue.getClass().getInterfaces(),
                    new ProxyPreparedStatementHandler(returnValue, performanceMonitor)
            );
        }
        return returnValue;
    }

    private boolean isPrepareStatement(Method method) {
        return method.getName().endsWith(METHOD_SUFFIX);
    }
}
