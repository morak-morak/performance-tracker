package com.morak.performancetracker.aop.web

import com.morak.performancetracker.context.Accumulator
import com.morak.performancetracker.context.ResultMapper
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.given
import org.mockito.Mockito
import org.springframework.http.HttpMethod
import javax.servlet.http.HttpServletRequest

class PerformanceInterceptorTest {
    @Test
    fun `HTTP요청 시간측정을 시작한다`() {
        // given
        val request = Mockito.mock(HttpServletRequest::class.java)
        given(request.method).willReturn(HttpMethod.GET.name)
        val monitor = WebMonitor()
        val accumulator = Accumulator(ResultMapper())
        val interceptor = PerformanceInterceptor(accumulator, monitor)
        // when
        interceptor.preHandle(request, null, null)
        // then
        org.junit.jupiter.api.Assertions.assertAll(
            { Assertions.assertThat(monitor.getRequestTime()).isNotZero() },
            { Assertions.assertThat(monitor.method).isEqualTo(HttpMethod.GET.name) }
        )
    }

    @Test
    fun `HTTP요청이 OPTIONS면 시간측정을 시작하지 않는다`() {
        // given
        val request = Mockito.mock(HttpServletRequest::class.java)
        given(request.method).willReturn(HttpMethod.OPTIONS.name)
        val monitor = WebMonitor()
        val accumulator = Accumulator(ResultMapper())
        val interceptor = PerformanceInterceptor(accumulator, monitor)
        // when
        interceptor.preHandle(request, null, null)
        // then
        Assertions.assertThat(monitor.getRequestTime()).isZero()
    }

    @Test
    fun `HTTP요청 시간측정을 종료한다`() {
        // given
        val request = Mockito.mock(HttpServletRequest::class.java)
        given(request.method).willReturn(HttpMethod.GET.name)
        val monitor = WebMonitor()
        val accumulator = Accumulator(ResultMapper())
        val interceptor = PerformanceInterceptor(accumulator, monitor)
        interceptor.preHandle(request, null, null)
        // when
        interceptor.postHandle(request, null, null, null)
        // then
        org.junit.jupiter.api.Assertions.assertAll(
            { Assertions.assertThat(monitor.getRequestTime()).isNotZero() },
            { Assertions.assertThat(monitor.method).isEqualTo(HttpMethod.GET.name) }
        )
    }

    @Test
    fun `HTTP요청이 OPTIONS면 시간측정을 종료하지 않는다`() {
        // given
        val request = Mockito.mock(HttpServletRequest::class.java)
        given(request.method).willReturn(HttpMethod.OPTIONS.name)
        val monitor = WebMonitor()
        val accumulator = Accumulator(ResultMapper())
        val interceptor = PerformanceInterceptor(accumulator, monitor)
        // when
        interceptor.postHandle(request, null, null, null)
        // then
        Assertions.assertThat(monitor.getRequestTime()).isZero()
    }
}
