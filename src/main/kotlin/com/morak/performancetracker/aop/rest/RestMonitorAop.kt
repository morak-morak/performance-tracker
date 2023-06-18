package com.morak.performancetracker.aop.rest

import com.morak.performancetracker.aop.util.ProxyFactoryBeanUtils
import com.morak.performancetracker.context.Accumulator
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.springframework.stereotype.Component

@Component
@Aspect
class RestMonitorAop(private val restMonitor: RestMonitor, private val accumulator: Accumulator) {
    @Around("execution(* org.springframework.boot.web.client.RestTemplateBuilder.build())")
    fun intercept(joinPoint: ProceedingJoinPoint): Any? {
        val returnValue = joinPoint.proceed()
        val advisor = RestAdvisor(RestPointcut(), RestAdvice(restMonitor, accumulator))
        return ProxyFactoryBeanUtils.createObject(returnValue, true, advisor)
    }
}
