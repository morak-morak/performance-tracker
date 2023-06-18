package com.morak.performancetracker.aop.rest

import org.aopalliance.aop.Advice
import org.springframework.aop.Pointcut
import org.springframework.aop.PointcutAdvisor

class RestAdvisor(private val pointcut: Pointcut, private val advice: Advice) : PointcutAdvisor {
    override fun getPointcut(): Pointcut {
        return pointcut
    }

    override fun getAdvice(): Advice {
        return advice
    }

    override fun isPerInstance(): Boolean {
        return true
    }
}
