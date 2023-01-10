package com.morak.performancetracker.aop.persistence;

import com.morak.performancetracker.context.Accumulator;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

public class StatementAdvice implements MethodInterceptor {

    private final QueryMonitor queryMonitor;
    private final Accumulator accumulator;

    public StatementAdvice(QueryMonitor queryMonitor, Accumulator accumulator) {
        this.queryMonitor = queryMonitor;
        this.accumulator = accumulator;
    }

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        queryMonitor.start();
        Object returnValue = invocation.proceed();
        queryMonitor.end();
        accumulator.add(queryMonitor);
        return returnValue;
    }
}
