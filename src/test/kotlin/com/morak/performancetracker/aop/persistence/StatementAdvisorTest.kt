package com.morak.performancetracker.aop.persistence

import com.morak.performancetracker.context.Accumulator
import com.morak.performancetracker.context.ResultMapper
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

internal class StatementAdvisorTest {
    @Test
    fun statement_advisor를_생성한다() {
        // given
        val pointcut = StatementPointcut()
        val advice = StatementAdvice(QueryMonitor(), Accumulator(ResultMapper()))

        // when
        val advisor = StatementAdvisor(pointcut, advice)

        // then
        Assertions.assertThat(advisor).isNotNull()
    }
}