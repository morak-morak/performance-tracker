package com.morak.performancetracker.aop.persistence

import com.morak.performancetracker.context.Accumulator
import com.morak.performancetracker.context.ResultMapper
import org.aopalliance.intercept.MethodInvocation
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(MockitoExtension::class)
internal class StatementAdviceTest {
    @Mock
    private val methodInvocation: MethodInvocation? = null
    @Test
    @Throws(Throwable::class)
    fun accumulator에_result가_쌓인다() {
        // given
        val queryMonitor = QueryMonitor()
        val accumulator = Accumulator(ResultMapper())
        val advice = StatementAdvice(queryMonitor, accumulator)

        // when
        val result = advice.invoke(methodInvocation!!)

        // then
        assertThat(accumulator.results[queryMonitor.signature]!!.size).isOne()
    }
}
