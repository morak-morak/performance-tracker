package com.morak.performancetracker.aop.persistence;

import com.morak.performancetracker.context.Accumulator;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.framework.ProxyFactoryBean;

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
        ProxyFactoryBean proxyFactoryBean = new ProxyFactoryBean();
        proxyFactoryBean.setTarget(returnValue);
        proxyFactoryBean.setProxyTargetClass(false);
        queryMonitor.setQuery((String) invocation.getArguments()[0]);
        proxyFactoryBean.addAdvisor(new StatementAdvisor(new StatementPointcut(), new StatementAdvice(queryMonitor, accumulator)));
        return proxyFactoryBean.getObject();
    }
}
