package com.morak.performancetracker.aop.rest

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.function.Executable

class RestMonitorTest {
    @Test
    fun 시간_측정을_시작한다() {
        // given
        val monitor = RestMonitor()
        // when
        monitor.start("https://example.com")
        // then
        Assertions.assertThat(monitor.startTime).isNotZero()
    }

    @Test
    fun 시간_측정을_종료한다() {
        // given
        val monitor = RestMonitor()
        monitor.start("https://example.com")
        // when
        monitor.end()
        // then
        Assertions.assertThat(monitor.elapsedTime).isNotZero()
    }

    @Test
    fun 모니터를_초기화한다() {
        // given
        val monitor = RestMonitor()
        monitor.start("https://example.com")
        monitor.end()
        // when
        monitor.clear()
        // then
        org.junit.jupiter.api.Assertions.assertAll(
            Executable { Assertions.assertThat(monitor.startTime).isZero() },
            Executable { Assertions.assertThat(monitor.elapsedTime).isZero() }
        )
    }
}
