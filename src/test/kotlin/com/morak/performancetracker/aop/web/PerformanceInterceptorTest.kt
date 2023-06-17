package com.morak.performancetracker.aop.web

import com.morak.performancetracker.context.Accumulator
import com.morak.performancetracker.context.ResultMapper
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.function.Executable
import org.mockito.BDDMockito
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.http.HttpMethod
import javax.servlet.http.HttpServletRequest

@ExtendWith(MockitoExtension::class)
internal class PerformanceInterceptorTest {
    @Mock
    private val request: HttpServletRequest? = null
    @Test
    fun HTTP요청_시간측정을_시작한다() {
        // given
        BDDMockito.given(request!!.method).willReturn(HttpMethod.GET.name)
        val monitor = WebMonitor()
        val accumulator = Accumulator(ResultMapper())
        val interceptor = PerformanceInterceptor(accumulator, monitor)
        // when
        interceptor.preHandle(request, null, null)
        // then
        org.junit.jupiter.api.Assertions.assertAll(
            Executable { Assertions.assertThat(monitor.getRequestTime()).isNotZero() },
            Executable { Assertions.assertThat(monitor.method).isEqualTo(HttpMethod.GET.name) }
        )
    }

    @Test
    fun HTTP요청이_OPTIONS면_시간측정을_시작하지_않는다() {
        // given
        BDDMockito.given(request!!.method).willReturn(HttpMethod.OPTIONS.name)
        val monitor = WebMonitor()
        val accumulator = Accumulator(ResultMapper())
        val interceptor = PerformanceInterceptor(accumulator, monitor)
        // when
        interceptor.preHandle(request, null, null)
        // then
        Assertions.assertThat(monitor.getRequestTime()).isZero()
    }

    @Test
    fun HTTP요청_시간측정을_종료한다() {
        // given
        BDDMockito.given(request!!.method).willReturn(HttpMethod.GET.name)
        val monitor = WebMonitor()
        val accumulator = Accumulator(ResultMapper())
        val interceptor = PerformanceInterceptor(accumulator, monitor)
        interceptor.preHandle(request, null, null)
        // when
        interceptor.postHandle(request, null, null, null)
        // then
        org.junit.jupiter.api.Assertions.assertAll(
            Executable { Assertions.assertThat(monitor.getRequestTime()).isNotZero() },
            Executable { Assertions.assertThat(monitor.method).isEqualTo(HttpMethod.GET.name) }
        )
    }

    @Test
    fun HTTP요청이_OPTIONS면_시간측정을_종료하지_않는다() {
        // given
        BDDMockito.given(request!!.method).willReturn(HttpMethod.OPTIONS.name)
        val monitor = WebMonitor()
        val accumulator = Accumulator(ResultMapper())
        val interceptor = PerformanceInterceptor(accumulator, monitor)
        // when
        interceptor.postHandle(request, null, null, null)
        // then
        Assertions.assertThat(monitor.getRequestTime()).isZero()
    }
}
