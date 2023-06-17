package com.morak.performancetracker.aop.web

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.*
import org.mockito.Mockito
import org.springframework.http.HttpMethod
import javax.servlet.http.HttpServletRequest

class WebMonitorTest {
    @Test
    fun 웹요청_시간측정을_시작한다() {
        // given
        val request = Mockito.mock(HttpServletRequest::class.java)
        given(request.requestURI).willReturn("/hello")
        given(request.method).willReturn(HttpMethod.GET.name)
        val monitor = WebMonitor()
        // when
        monitor.start(request)

        // then
        org.junit.jupiter.api.Assertions.assertAll(
            { Assertions.assertThat(monitor.method).isEqualTo(HttpMethod.GET.name) },
            { Assertions.assertThat(monitor.getRequestTime()).isNotZero() }
        )
    }

    @Test
    fun 웹요청_시간측정을_종료한다() {
        // given
        val request = Mockito.mock(HttpServletRequest::class.java)
        given(request.requestURI).willReturn("/hello")
        given(request.method).willReturn(HttpMethod.GET.name)
        val monitor = WebMonitor()
        monitor.start(request)
        // when
        monitor.end()

        // then
        Assertions.assertThat(monitor.elapsed).isNotZero()
    }

    @Test
    fun 웹요청_정보를_초기화한다() {
        // given
        val request = Mockito.mock(HttpServletRequest::class.java)
        given(request.requestURI).willReturn("/hello")
        given(request.method).willReturn(HttpMethod.GET.name)
        val monitor = WebMonitor()
        monitor.start(request)
        monitor.end()

        // when
        monitor.clear()

        // then
        org.junit.jupiter.api.Assertions.assertAll(
            { Assertions.assertThat(monitor.uri).isNull() },
            { Assertions.assertThat(monitor.method).isNull() },
            { Assertions.assertThat(monitor.getRequestTime()).isZero() },
            { Assertions.assertThat(monitor.elapsed).isZero() }
        )
    }
}
