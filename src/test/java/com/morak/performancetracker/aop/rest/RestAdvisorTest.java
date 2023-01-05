package com.morak.performancetracker.aop.rest;

import static org.assertj.core.api.Assertions.assertThat;

import com.morak.performancetracker.context.Accumulator;
import com.morak.performancetracker.context.ResultMapper;
import org.junit.jupiter.api.Test;

class RestAdvisorTest {

    @Test
    void advisor를_생성한다() {
        // given
        RestPointcut pointcut = new RestPointcut();
        RestAdvice advice = new RestAdvice(new RestMonitor(), new Accumulator(new ResultMapper()));
        // when
        RestAdvisor advisor = new RestAdvisor(pointcut, advice);
        // then
        assertThat(advisor).isNotNull();
    }
}
