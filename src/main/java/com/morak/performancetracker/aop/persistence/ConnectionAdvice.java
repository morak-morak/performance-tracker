package com.morak.performancetracker.aop.persistence;

import com.morak.performancetracker.aop.util.ProxyFactoryBeanUtils;
import com.morak.performancetracker.context.Accumulator;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

public class ConnectionAdvice implements MethodInterceptor {

    private final QueryMonitor queryMonitor;
    private final Accumulator accumulator;

    public ConnectionAdvice(QueryMonitor queryMonitor, Accumulator accumulator) {
        this.queryMonitor = queryMonitor;
        this.accumulator = accumulator;
    }

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        Object returnValue = invocation.proceed();
        queryMonitor.setQuery((String) invocation.getArguments()[0]);
        StatementAdvisor advisor = new StatementAdvisor(new StatementPointcut(),
                new StatementAdvice(queryMonitor, accumulator));
        return ProxyFactoryBeanUtils.createObject(returnValue, false, advisor);
    }
}
