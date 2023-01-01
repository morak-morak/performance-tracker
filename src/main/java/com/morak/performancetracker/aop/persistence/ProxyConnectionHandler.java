package com.morak.performancetracker.aop.persistence;

import com.morak.performancetracker.context.Accumulator;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class ProxyConnectionHandler implements InvocationHandler {

    private static final String METHOD_SUFFIX = "prepareStatement";

    private final Object connection;
    private final Accumulator accumulator;
    private final QueryMonitor queryMonitor;

    public ProxyConnectionHandler(Object connection, Accumulator accumulator, QueryMonitor queryMonitor) {
        this.connection = connection;
        this.accumulator = accumulator;
        this.queryMonitor = queryMonitor;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object returnValue = method.invoke(connection, args);
        if (isStatement(method)) {
            queryMonitor.setQuery((String) args[0]);
            return Proxy.newProxyInstance(
                    returnValue.getClass().getClassLoader(),
                    returnValue.getClass().getInterfaces(),
                    new ProxyPreparedStatementHandler(returnValue, accumulator, queryMonitor)
            );
        }
        return returnValue;
    }

    private boolean isStatement(Method method) {
        return method.getName().endsWith(METHOD_SUFFIX);
    }
}
