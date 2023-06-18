package com.morak.performancetracker.aop.rest

import org.springframework.aop.support.StaticMethodMatcherPointcut
import java.lang.reflect.Method

class RestPointcut : StaticMethodMatcherPointcut() {
    override fun matches(method: Method, ignored: Class<*>): Boolean {
        return method.name.contains("execute")
    }
}
