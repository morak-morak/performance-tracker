package com.morak.performancetracker.aop.rest

import com.morak.performancetracker.context.Accumulator
import org.aopalliance.intercept.MethodInterceptor
import org.aopalliance.intercept.MethodInvocation

class RestAdvice(private val restMonitor: RestMonitor, private val accumulator: Accumulator) : MethodInterceptor {
    override fun invoke(invocation: MethodInvocation): Any? {
        val argument = invocation.arguments[0]
        restMonitor.start(argument.toString())
        val returnValue = invocation.proceed()
        restMonitor.end()
        accumulator.add(restMonitor)
        return returnValue
    }
}
