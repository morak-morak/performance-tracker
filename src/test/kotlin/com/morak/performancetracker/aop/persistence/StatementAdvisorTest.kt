package com.morak.performancetracker.aop.persistence

import com.morak.performancetracker.context.Accumulator
import com.morak.performancetracker.context.ResultMapper
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

class StatementAdvisorTest {
    @Test
    fun `statement advisor를 생성한다`() {
        // given
        val pointcut = StatementPointcut()
        val advice = StatementAdvice(QueryMonitor(), Accumulator(ResultMapper()))

        // when
        val advisor = StatementAdvisor(pointcut, advice)

        // then
        Assertions.assertThat(advisor).isNotNull()
    }
}
