package com.morak.performancetracker.aop.util

import com.morak.performancetracker.aop.util.ProxyFactoryBeanUtils.createObject
import org.aopalliance.aop.Advice
import org.aopalliance.intercept.MethodInterceptor
import org.aopalliance.intercept.MethodInvocation
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.aop.Pointcut
import org.springframework.aop.PointcutAdvisor
import org.springframework.aop.support.AopUtils
import org.springframework.aop.support.StaticMethodMatcherPointcut
import java.lang.reflect.Method

internal class ProxyFactoryBeanUtilsTest {
    @Test
    fun cglib_proxy_객체를_만들_수_있다() {
        // given
        val testObject = TestCglibObject()

        // when
        val result = createObject(testObject, true, TestAdvisor())

        // then
        Assertions.assertThat(AopUtils.isCglibProxy(result)).isTrue()
    }

    @Test
    fun jdk_dynamic_proxy_객체를_만들_수_있다() {
        // given
        val testObject = TestDynamicObject()

        // when
        val result = createObject(testObject, false, TestAdvisor())

        // then
        Assertions.assertThat(AopUtils.isJdkDynamicProxy(result)).isTrue()
    }

    internal open class TestCglibObject

    internal class TestAdvisor : PointcutAdvisor {
        override fun getPointcut(): Pointcut {
            return TestPointcut()
        }

        override fun getAdvice(): Advice {
            return MethodInterceptor { invocation: MethodInvocation? -> Any() }
        }

        override fun isPerInstance(): Boolean {
            return false
        }
    }

    internal class TestPointcut : StaticMethodMatcherPointcut() {
        override fun matches(method: Method, targetClass: Class<*>?): Boolean {
            return false
        }
    }

    internal class TestDynamicObject : TestInterface
}

internal interface TestInterface
