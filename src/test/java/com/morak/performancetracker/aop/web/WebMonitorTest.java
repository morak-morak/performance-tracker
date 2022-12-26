package com.morak.performancetracker.aop.web;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import javax.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpMethod;

@ExtendWith(MockitoExtension.class)
class WebMonitorTest {

    @Mock
    private HttpServletRequest request;

    @Test
    void 웹요청_시간측정을_시작한다() {
        // given
        given(request.getRequestURI()).willReturn("/hello");
        given(request.getMethod()).willReturn(HttpMethod.GET.name());

        WebMonitor monitor = new WebMonitor();
        // when
        monitor.start(request);

        // then
        Assertions.assertAll(
                () -> assertThat(monitor.getMethod()).isEqualTo(HttpMethod.GET.name()),
                () -> assertThat(monitor.getRequestTime()).isNotZero()
        );
    }

    @Test
    void 웹요청_시간측정을_종료한다() {
        // given
        given(request.getRequestURI()).willReturn("/hello");
        given(request.getMethod()).willReturn(HttpMethod.GET.name());

        WebMonitor monitor = new WebMonitor();
        monitor.start(request);
        // when
        monitor.end();

        // then
        assertThat(monitor.getElapsed()).isNotZero();
    }

    @Test
    void 웹요청_정보를_초기화한다() {
        // given
        given(request.getRequestURI()).willReturn("/hello");
        given(request.getMethod()).willReturn(HttpMethod.GET.name());

        WebMonitor monitor = new WebMonitor();
        // when
        monitor.start(request);
        monitor.end();
        // when
        monitor.clear();

        // then
        Assertions.assertAll(
                () -> assertThat(monitor.getUri()).isNull(),
                () -> assertThat(monitor.getMethod()).isNull(),
                () -> assertThat(monitor.getRequestTime()).isZero(),
                () -> assertThat(monitor.getElapsed()).isZero()
        );
    }
}
