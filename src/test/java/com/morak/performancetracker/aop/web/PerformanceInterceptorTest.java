package com.morak.performancetracker.aop.web;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import com.morak.performancetracker.context.Accumulator;
import com.morak.performancetracker.context.ResultMapper;
import javax.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpMethod;

@ExtendWith(MockitoExtension.class)
class PerformanceInterceptorTest {

    @Mock
    private HttpServletRequest request;

    @Test
    void HTTP요청_시간측정을_시작한다() {
        // given
        given(request.getMethod()).willReturn(HttpMethod.GET.name());
        WebMonitor monitor = new WebMonitor();
        Accumulator accumulator = new Accumulator(new ResultMapper());
        PerformanceInterceptor interceptor = new PerformanceInterceptor(accumulator, monitor);
        // when
        interceptor.preHandle(request, null, null);
        // then
        Assertions.assertAll(
                () -> assertThat(monitor.getRequestTime()).isNotZero(),
                () -> assertThat(monitor.getMethod()).isEqualTo(HttpMethod.GET.name())
        );
    }

    @Test
    void HTTP요청이_OPTIONS면_시간측정을_시작하지_않는다() {
        // given
        given(request.getMethod()).willReturn(HttpMethod.OPTIONS.name());
        WebMonitor monitor = new WebMonitor();
        Accumulator accumulator = new Accumulator(new ResultMapper());
        PerformanceInterceptor interceptor = new PerformanceInterceptor(accumulator, monitor);
        // when
        interceptor.preHandle(request, null, null);
        // then
        assertThat(monitor.getRequestTime()).isZero();
    }

    @Test
    void HTTP요청_시간측정을_종료한다() {
        // given
        given(request.getMethod()).willReturn(HttpMethod.GET.name());
        WebMonitor monitor = new WebMonitor();
        Accumulator accumulator = new Accumulator(new ResultMapper());
        PerformanceInterceptor interceptor = new PerformanceInterceptor(accumulator, monitor);
        interceptor.preHandle(request, null, null);
        // when
        interceptor.postHandle(request, null, null, null);
        // then
        Assertions.assertAll(
                () -> assertThat(monitor.getRequestTime()).isNotZero(),
                () -> assertThat(monitor.getMethod()).isEqualTo(HttpMethod.GET.name())
        );
    }

    @Test
    void HTTP요청이_OPTIONS면_시간측정을_종료하지_않는다() {
        // given
        given(request.getMethod()).willReturn(HttpMethod.OPTIONS.name());
        WebMonitor monitor = new WebMonitor();
        Accumulator accumulator = new Accumulator(new ResultMapper());
        PerformanceInterceptor interceptor = new PerformanceInterceptor(accumulator, monitor);
        // when
        interceptor.postHandle(request, null, null, null);
        // then
        assertThat(monitor.getRequestTime()).isZero();
    }
}
