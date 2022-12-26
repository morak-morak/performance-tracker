package com.morak.performancetracker.aop.persistence;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class QueryMonitorTest {

    @Test
    void 쿼리시간_측정을_시작한다() {
        // given
        QueryMonitor monitor = new QueryMonitor();
        // when
        monitor.start();
        // then
        assertThat(monitor.getStartTime()).isNotZero();
    }

    @Test
    void 쿼리시간_측정을_종료한다() {
        // given
        QueryMonitor monitor = new QueryMonitor();
        monitor.start();
        // when
        monitor.end();
        // then
        assertThat(monitor.getQueryTime()).isNotZero();
    }

    @Test
    void 쿼리시간_측정종료시_시작시간이_0이상이어야_한다() {
        // given
        QueryMonitor monitor = new QueryMonitor();
        // when & then
        assertThatThrownBy(() -> monitor.end()).isInstanceOf(IllegalStateException.class);
    }

    @Test
    void 모니터를_초기화한다() {
        // given
        QueryMonitor monitor = new QueryMonitor();
        // when
        monitor.clear();
        // then
        Assertions.assertAll(
                () -> assertThat(monitor.getQueryTime()).isZero(),
                () -> assertThat(monitor.getQueryCount()).isZero(),
                () -> assertThat(monitor.getStartTime()).isZero()
        );
    }
}
