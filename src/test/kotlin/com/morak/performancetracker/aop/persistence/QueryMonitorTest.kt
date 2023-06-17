package com.morak.performancetracker.aop.persistence

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

class QueryMonitorTest {
    @Test
    fun `쿼리시간 측정을 시작한다`() {
        // given
        val monitor = QueryMonitor()
        // when
        monitor.start()
        // then
        Assertions.assertThat(monitor.startTime).isNotZero()
    }

    @Test
    fun `쿼리시간 측정을 종료한다`() {
        // given
        val monitor = QueryMonitor()
        monitor.start()
        // when
        monitor.end()
        // then
        Assertions.assertThat(monitor.queryTime).isNotZero()
    }

    @Test
    fun `쿼리시간 측정종료시 시작시간이 0이상이어야 한다`() {
        // given
        val monitor = QueryMonitor()
        // when & then
        Assertions.assertThatThrownBy { monitor.end() }.isInstanceOf(IllegalStateException::class.java)
    }

    @Test
    fun `모니터를 초기화한다`() {
        // given
        val monitor = QueryMonitor()
        // when
        monitor.clear()
        // then
        org.junit.jupiter.api.Assertions.assertAll(
            { Assertions.assertThat(monitor.queryTime).isZero() },
            { Assertions.assertThat(monitor.startTime).isZero() }
        )
    }
}
