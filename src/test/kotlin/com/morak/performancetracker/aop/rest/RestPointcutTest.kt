package com.morak.performancetracker.aop.rest

import org.assertj.core.api.AssertionsForClassTypes
import org.junit.jupiter.api.Test
import org.springframework.http.HttpMethod
import org.springframework.web.client.RequestCallback
import org.springframework.web.client.ResponseExtractor
import org.springframework.web.client.RestTemplate
import java.lang.reflect.Type
import java.net.URI

class RestPointcutTest {
    @Test
    fun `메소드 이름이 일치하면 true를 반환한다`() {
        // given
        val pointcut = RestPointcut()
        // when
        val isMatch = pointcut.matches(
            RestTemplate::class.java.getMethod(
                "execute",
                URI::class.java,
                HttpMethod::class.java,
                RequestCallback::class.java,
                ResponseExtractor::class.java
            ), Any::class.java
        )
        // then
        AssertionsForClassTypes.assertThat(isMatch).isTrue()
    }

    @Test
    fun `메소드 이름이 일치하지 않으면 false를 반환한다`() {
        // given
        val pointcut = RestPointcut()
        // when
        val isMatch =
            pointcut.matches(
                RestTemplate::class.java.getMethod("responseEntityExtractor", Type::class.java),
                Any::class.java
            )
        // then
        AssertionsForClassTypes.assertThat(isMatch).isFalse()
    }
}
