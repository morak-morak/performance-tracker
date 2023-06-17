package com.morak.performancetracker.aop.rest

import com.morak.performancetracker.context.Accumulator
import com.morak.performancetracker.context.ResultMapper
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

internal class RestAdvisorTest {
    @Test
    fun advisor를_생성한다() {
        // given
        val pointcut = RestPointcut()
        val advice = RestAdvice(RestMonitor(), Accumulator(ResultMapper()))
        // when
        val advisor = RestAdvisor(pointcut, advice)
        // then
        Assertions.assertThat(advisor).isNotNull()
    }
}
