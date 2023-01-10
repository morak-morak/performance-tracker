package com.morak.performancetracker.aop.persistence;

import com.morak.performancetracker.aop.util.ProxyFactoryBeanUtils;
import com.morak.performancetracker.context.Accumulator;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class QueryMonitorAop {

    private final QueryMonitor queryMonitor;
    private final Accumulator accumulator;

    public QueryMonitorAop(QueryMonitor queryMonitor, Accumulator accumulator) {
        this.queryMonitor = queryMonitor;
        this.accumulator = accumulator;
    }

    @Around("execution(* javax.sql.DataSource.getConnection())")
    public Object datasource(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Object returnValue = proceedingJoinPoint.proceed();
        ConnectionAdvisor advisor = new ConnectionAdvisor(new ConnectionPointcut(),
                new ConnectionAdvice(queryMonitor, accumulator));
        return ProxyFactoryBeanUtils.createObject(returnValue, false, advisor);
    }
}
