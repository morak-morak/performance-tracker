package com.morak.performancetracker.aop.persistence

import com.morak.performancetracker.aop.util.ProxyFactoryBeanUtils
import com.morak.performancetracker.context.Accumulator
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.springframework.stereotype.Component

@Component
@Aspect
class QueryMonitorAop(private val queryMonitor: QueryMonitor, private val accumulator: Accumulator) {
    @Around("execution(* javax.sql.DataSource.getConnection())")
    fun datasource(proceedingJoinPoint: ProceedingJoinPoint): Any? {
        val returnValue = proceedingJoinPoint.proceed()
        val advisor = ConnectionAdvisor(
            ConnectionPointcut(),
            ConnectionAdvice(queryMonitor, accumulator)
        )
        return ProxyFactoryBeanUtils.createObject(returnValue, false, advisor)
    }
}
