package com.morak.performancetracker.aop.persistence

import org.springframework.aop.support.StaticMethodMatcherPointcut
import java.lang.reflect.Method

class ConnectionPointcut : StaticMethodMatcherPointcut() {
    override fun matches(method: Method, targetClass: Class<*>?): Boolean {
        return method.name.endsWith(METHOD_SUFFIX)
    }

    companion object {
        private const val METHOD_SUFFIX = "prepareStatement"
    }
}
