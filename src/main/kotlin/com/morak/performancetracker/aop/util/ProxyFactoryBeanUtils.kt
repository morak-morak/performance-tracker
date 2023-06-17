package com.morak.performancetracker.aop.util

import org.springframework.aop.PointcutAdvisor
import org.springframework.aop.framework.ProxyFactoryBean

object ProxyFactoryBeanUtils {
    @JvmStatic
    fun createObject(target: Any?, setProxyTargetClass: Boolean, advisor: PointcutAdvisor?): Any {
        val proxyFactoryBean = ProxyFactoryBean()
        proxyFactoryBean.setTarget(target)
        proxyFactoryBean.isProxyTargetClass = setProxyTargetClass
        proxyFactoryBean.addAdvisor(advisor)
        return proxyFactoryBean.getObject()
    }
}