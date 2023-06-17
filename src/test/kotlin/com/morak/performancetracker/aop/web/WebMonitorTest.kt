package com.morak.performancetracker.aop.web

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
class WebMonitorTest {
    @Mock
    private val request: HttpServletRequest? = null

    @Test
    fun 웹요청_시간측정을_시작한다() {
        // given
        BDDMockito.given(request!!.requestURI).willReturn("/hello")
        BDDMockito.given(request.method).willReturn(HttpMethod.GET.name)
        val monitor = WebMonitor()
        // when
        monitor.start(request)

        // then
        org.junit.jupiter.api.Assertions.assertAll(
            Executable { Assertions.assertThat(monitor.method).isEqualTo(HttpMethod.GET.name) },
            Executable { Assertions.assertThat(monitor.getRequestTime()).isNotZero() }
        )
    }

    @Test
    fun 웹요청_시간측정을_종료한다() {
        // given
        BDDMockito.given(request!!.requestURI).willReturn("/hello")
        BDDMockito.given(request.method).willReturn(HttpMethod.GET.name)
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
        BDDMockito.given(request!!.requestURI).willReturn("/hello")
        BDDMockito.given(request.method).willReturn(HttpMethod.GET.name)
        val monitor = WebMonitor()
        // when
        monitor.start(request)
        monitor.end()
        // when
        monitor.clear()

        // then
        org.junit.jupiter.api.Assertions.assertAll(
            Executable { Assertions.assertThat(monitor.uri).isNull() },
            Executable { Assertions.assertThat(monitor.method).isNull() },
            Executable { Assertions.assertThat(monitor.getRequestTime()).isZero() },
            Executable { Assertions.assertThat(monitor.elapsed).isZero() }
        )
    }
}
