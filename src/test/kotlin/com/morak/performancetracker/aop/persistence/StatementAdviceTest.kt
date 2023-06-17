package com.morak.performancetracker.aop.persistence

import com.morak.performancetracker.context.Accumulator
import com.morak.performancetracker.context.ResultMapper
import org.aopalliance.intercept.MethodInvocation
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.mockito.Mockito

class StatementAdviceTest {

    @Test
    fun accumulator에_result가_쌓인다() {
        // given
        val methodInvocation = Mockito.mock(MethodInvocation::class.java)

        val queryMonitor = QueryMonitor()
        queryMonitor.query = "insert into "
        val accumulator = Accumulator(ResultMapper())
        val advice = StatementAdvice(queryMonitor, accumulator)

        // when
        val result = advice.invoke(methodInvocation)

        // then
        assertThat(accumulator.results[queryMonitor.signature]?.size).isOne()
    }
}
