package com.morak.performancetracker.aop.persistence;

import com.morak.performancetracker.context.Accumulator;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class ProxyPreparedStatementHandler implements InvocationHandler {

    private static final String EXECUTION_PREFIX = "execute";

    private final Object preparedStatement;
    private final Accumulator accumulator;
    private final QueryMonitor queryMonitor;

    public ProxyPreparedStatementHandler(Object preparedStatement, Accumulator accumulator, QueryMonitor queryMonitor) {
        this.preparedStatement = preparedStatement;
        this.accumulator = accumulator;
        this.queryMonitor = queryMonitor;
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
        queryMonitor.start();
        Object returnValue = method.invoke(preparedStatement, args);
        queryMonitor.end();
        accumulator.add(queryMonitor);
        return returnValue;
    }
}
