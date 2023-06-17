package com.morak.performancetracker.aop.persistence

import org.springframework.aop.support.StaticMethodMatcherPointcut
import java.lang.reflect.Method

class StatementPointcut : StaticMethodMatcherPointcut() {
    override fun matches(method: Method, targetClass: Class<*>?): Boolean {
        return method.name.contains(EXECUTION_PREFIX)
    }

    companion object {
        private const val EXECUTION_PREFIX = "execute"
    }
}
