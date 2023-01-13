package com.morak.performancetracker.aop.persistence;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import com.morak.performancetracker.context.Accumulator;
import com.morak.performancetracker.context.ResultMapper;
import org.junit.jupiter.api.Test;

class ConnectionAdvisorTest {

    @Test
    void connection_advisor를_생성한다() {
        // given
        ConnectionPointcut pointcut = new ConnectionPointcut();
        ConnectionAdvice advice = new ConnectionAdvice(new QueryMonitor(),
                new Accumulator(new ResultMapper()));

        // when
        ConnectionAdvisor advisor = new ConnectionAdvisor(pointcut, advice);

        // then
        assertThat(advisor).isNotNull();
    }
}
