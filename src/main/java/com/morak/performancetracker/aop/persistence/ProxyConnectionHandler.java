package com.morak.performancetracker.aop.persistence;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class ProxyConnectionHandler implements InvocationHandler {

    private static final String METHOD_SUFFIX = "prepareStatement";

    private final Object connection;
    private final QueryMonitor queryMonitor;

    public ProxyConnectionHandler(Object connection, QueryMonitor queryMonitor) {
        this.connection = connection;
        this.queryMonitor = queryMonitor;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object returnValue = method.invoke(connection, args);
        queryMonitor.setQuery((String) args[0]);
        if (isStatement(method)) {
            return Proxy.newProxyInstance(
                    returnValue.getClass().getClassLoader(),
                    returnValue.getClass().getInterfaces(),
                    new ProxyPreparedStatementHandler(returnValue, queryMonitor)
            );
        }
        return returnValue;
    }

    private boolean isStatement(Method method) {
        return method.getName().endsWith(METHOD_SUFFIX);
    }
}
