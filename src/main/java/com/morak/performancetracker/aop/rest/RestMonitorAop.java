package com.morak.performancetracker.aop.rest;

import com.morak.performancetracker.context.Accumulator;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.aop.framework.ProxyFactoryBean;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class RestMonitorAop {

    private final RestMonitor restMonitor;
    private final Accumulator accumulator;

    public RestMonitorAop(RestMonitor restMonitor, Accumulator accumulator) {
        this.restMonitor = restMonitor;
        this.accumulator = accumulator;
    }

    @Around("execution(* org.springframework.boot.web.client.RestTemplateBuilder.build())")
    public Object intercept(ProceedingJoinPoint joinPoint) throws Throwable {
        Object returnValue = joinPoint.proceed();
        ProxyFactoryBean proxyFactoryBean = new ProxyFactoryBean();
        proxyFactoryBean.setTarget(returnValue);
        proxyFactoryBean.setProxyTargetClass(true);
        proxyFactoryBean.addAdvisor(new RestAdvisor(new RestPointcut(), new RestAdvice(restMonitor, accumulator)));
        return proxyFactoryBean.getObject();
    }
}
