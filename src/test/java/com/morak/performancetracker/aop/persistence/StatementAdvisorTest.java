package com.morak.performancetracker.aop.persistence;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import com.morak.performancetracker.context.Accumulator;
import com.morak.performancetracker.context.ResultMapper;
import org.junit.jupiter.api.Test;

class StatementAdvisorTest {

    @Test
    void statement_advisor를_생성한다() {
        // given
        StatementPointcut pointcut = new StatementPointcut();
        StatementAdvice advice = new StatementAdvice(new QueryMonitor(), new Accumulator(new ResultMapper()));

        // when
        StatementAdvisor advisor = new StatementAdvisor(pointcut, advice);

        // then
        assertThat(advisor).isNotNull();
    }

}
