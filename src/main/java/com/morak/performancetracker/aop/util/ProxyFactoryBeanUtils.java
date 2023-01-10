package com.morak.performancetracker.aop.util;

import java.lang.reflect.Proxy;
import org.springframework.aop.PointcutAdvisor;
import org.springframework.aop.framework.ProxyFactoryBean;

public class ProxyFactoryBeanUtils {

    private ProxyFactoryBeanUtils() {
    }

    public static Object createObject(Object target, boolean setProxyTargetClass, PointcutAdvisor advisor) {
        ProxyFactoryBean proxyFactoryBean = new ProxyFactoryBean();
        proxyFactoryBean.setTarget(target);
        proxyFactoryBean.setProxyTargetClass(setProxyTargetClass);
        proxyFactoryBean.addAdvisor(advisor);
        return proxyFactoryBean.getObject();
    }
}
