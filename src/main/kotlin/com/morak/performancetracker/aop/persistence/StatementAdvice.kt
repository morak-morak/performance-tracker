package com.morak.performancetracker.aop.persistence

import com.morak.performancetracker.context.Accumulator
import org.aopalliance.intercept.MethodInterceptor
import org.aopalliance.intercept.MethodInvocation

class StatementAdvice(
    private val queryMonitor: QueryMonitor,
    private val accumulator: Accumulator,
) : MethodInterceptor {
    override fun invoke(invocation: MethodInvocation): Any? {
        queryMonitor.start()
        val returnValue = invocation.proceed()
        queryMonitor.end()
        accumulator.add(queryMonitor)
        return returnValue
    }
}
