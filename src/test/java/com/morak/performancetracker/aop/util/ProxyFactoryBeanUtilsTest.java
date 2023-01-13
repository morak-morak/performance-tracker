package com.morak.performancetracker.aop.util;

import static org.assertj.core.api.Assertions.assertThat;

import java.lang.reflect.Method;
import org.aopalliance.aop.Advice;
import org.aopalliance.intercept.MethodInterceptor;
import org.junit.jupiter.api.Test;
import org.springframework.aop.Pointcut;
import org.springframework.aop.PointcutAdvisor;
import org.springframework.aop.support.AopUtils;
import org.springframework.aop.support.StaticMethodMatcherPointcut;

class ProxyFactoryBeanUtilsTest {

    @Test
    void cglib_proxy_객체를_만들_수_있다() {
        // given
        TestCglibObject testObject = new TestCglibObject();

        // when
        Object result = ProxyFactoryBeanUtils.createObject(testObject, true, new TestAdvisor());

        // then
        assertThat(AopUtils.isCglibProxy(result)).isTrue();
    }

    @Test
    void jdk_dynamic_proxy_객체를_만들_수_있다() {
        // given
        TestDynamicObject testObject = new TestDynamicObject();

        // when
        Object result = ProxyFactoryBeanUtils.createObject(testObject, false, new TestAdvisor());

        // then
        assertThat(AopUtils.isJdkDynamicProxy(result)).isTrue();
    }

    static class TestCglibObject {

    }

    static class TestAdvisor implements PointcutAdvisor {

        @Override
        public Pointcut getPointcut() {
            return new TestPointcut();
        }

        @Override
        public Advice getAdvice() {
            return (MethodInterceptor) invocation -> new Object();
        }

        @Override
        public boolean isPerInstance() {
            return false;
        }

    }

    static class TestPointcut extends StaticMethodMatcherPointcut {

        @Override
        public boolean matches(Method method, Class<?> targetClass) {
            return false;
        }
    }

    static final class TestDynamicObject implements TestInterface {
    }
}

interface TestInterface {

}
