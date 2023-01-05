package com.morak.performancetracker.aop.rest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.BDDMockito.given;

import com.morak.performancetracker.context.Accumulator;
import com.morak.performancetracker.context.ResultMapper;
import org.aopalliance.intercept.MethodInvocation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class RestAdviceTest {

    @Mock
    private MethodInvocation methodInvocation;

    @Test
    void REST_요청_시간을_측정한다() throws Throwable {
        // given
        given(methodInvocation.getArguments()).willReturn(new Object[]{"https://example.com"});
        given(methodInvocation.proceed()).willReturn(new Object());

        Accumulator accumulator = new Accumulator(new ResultMapper());
        RestAdvice advice = new RestAdvice(new RestMonitor(), accumulator);

        // when
        Object result = advice.invoke(methodInvocation);
        // then
        Assertions.assertAll(
                () -> assertThat(accumulator.getResults().size()).isOne(),
                () -> assertThat(result).isNotNull()
        );
    }
}
