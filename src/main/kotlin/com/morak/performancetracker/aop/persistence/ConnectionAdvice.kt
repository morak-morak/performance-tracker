package com.morak.performancetracker.aop.persistence

import com.morak.performancetracker.aop.util.ProxyFactoryBeanUtils
import com.morak.performancetracker.context.Accumulator
import org.aopalliance.intercept.MethodInterceptor
import org.aopalliance.intercept.MethodInvocation

class ConnectionAdvice(
    private val queryMonitor: QueryMonitor,
    private val accumulator: Accumulator,
) : MethodInterceptor {
    override fun invoke(invocation: MethodInvocation): Any {
        val returnValue = invocation.proceed()
        queryMonitor.query = invocation.arguments[0] as String
        val advisor = StatementAdvisor(
            StatementPointcut(),
            StatementAdvice(queryMonitor, accumulator)
        )
        return ProxyFactoryBeanUtils.createObject(returnValue, false, advisor)
    }
}
