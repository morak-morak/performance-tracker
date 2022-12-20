package com.morak.performancetracker.aop.persistence;

import java.lang.reflect.Proxy;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class PerformanceMonitorAop {

    private final QueryMonitor queryMonitor;

    public PerformanceMonitorAop(QueryMonitor queryMonitor) {
        this.queryMonitor = queryMonitor;
    }

    @Around("execution(* javax.sql.DataSource.getConnection())")
    public Object datasource(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Object returnValue = proceedingJoinPoint.proceed();
        return Proxy.newProxyInstance(
                returnValue.getClass().getClassLoader(),
                returnValue.getClass().getInterfaces(),
                new ProxyConnectionHandler(returnValue, queryMonitor)
        );
    }

    public QueryMonitor getPerformanceMonitor() {
        return queryMonitor;
    }
}
