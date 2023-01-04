package com.morak.performancetracker.aop.rest;

import com.morak.performancetracker.context.Accumulator;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

public class RestAdvice implements MethodInterceptor {

    private final RestMonitor restMonitor;
    private final Accumulator accumulator;

    public RestAdvice(RestMonitor restMonitor, Accumulator accumulator) {
        this.restMonitor = restMonitor;
        this.accumulator = accumulator;
    }

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        Object argument = invocation.getArguments()[0];
        restMonitor.start(argument.toString());
        Object returnValue = invocation.proceed();
        restMonitor.end();
        accumulator.add(restMonitor);
        return returnValue;
    }
}
