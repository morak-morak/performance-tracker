package com.morak.performancetracker.aop.persistence

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.function.Executable

internal class QueryMonitorTest {
    @Test
    fun 쿼리시간_측정을_시작한다() {
        // given
        val monitor = QueryMonitor()
        // when
        monitor.start()
        // then
        Assertions.assertThat(monitor.startTime).isNotZero()
    }

    @Test
    fun 쿼리시간_측정을_종료한다() {
        // given
        val monitor = QueryMonitor()
        monitor.start()
        // when
        monitor.end()
        // then
        Assertions.assertThat(monitor.queryTime).isNotZero()
    }

    @Test
    fun 쿼리시간_측정종료시_시작시간이_0이상이어야_한다() {
        // given
        val monitor = QueryMonitor()
        // when & then
        Assertions.assertThatThrownBy { monitor.end() }.isInstanceOf(IllegalStateException::class.java)
    }

    @Test
    fun 모니터를_초기화한다() {
        // given
        val monitor = QueryMonitor()
        // when
        monitor.clear()
        // then
        org.junit.jupiter.api.Assertions.assertAll(
            Executable { Assertions.assertThat(monitor.queryTime).isZero() },
            Executable { Assertions.assertThat(monitor.startTime).isZero() }
        )
    }
}
