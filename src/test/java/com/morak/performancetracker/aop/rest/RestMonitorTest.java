package com.morak.performancetracker.aop.rest;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class RestMonitorTest {

    @Test
    void 시간_측정을_시작한다() {
        // given
        RestMonitor monitor = new RestMonitor();
        // when
        monitor.start("https://example.com");
        // then
        assertThat(monitor.getStartTime()).isNotZero();
    }

    @Test
    void 시간_측정을_종료한다() {
        // given
        RestMonitor monitor = new RestMonitor();
        monitor.start("https://example.com");
        // when
        monitor.end();
        // then
        assertThat(monitor.getElapsedTime()).isNotZero();
    }

    @Test
    void 모니터를_초기화한다() {
        // given
        RestMonitor monitor = new RestMonitor();
        monitor.start("https://example.com");
        monitor.end();
        // when
        monitor.clear();
        // then
        Assertions.assertAll(
                () -> assertThat(monitor.getStartTime()).isZero(),
                () -> assertThat(monitor.getElapsedTime()).isZero()
        );
    }
}
