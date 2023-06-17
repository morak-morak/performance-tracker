package com.morak.performancetracker.aop.persistence

import com.morak.performancetracker.context.Accumulator
import com.morak.performancetracker.context.ResultMapper
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

class ConnectionAdvisorTest {
    @Test
    fun `connection advisor를 생성한다`() {
        // given
        val pointcut = ConnectionPointcut()
        val advice = ConnectionAdvice(
            QueryMonitor(),
            Accumulator(ResultMapper())
        )

        // when
        val advisor = ConnectionAdvisor(pointcut, advice)

        // then
        Assertions.assertThat(advisor).isNotNull()
    }
}
