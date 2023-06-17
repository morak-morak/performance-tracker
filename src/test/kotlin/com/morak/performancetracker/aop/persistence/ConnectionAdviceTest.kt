package com.morak.performancetracker.aop.persistence

import com.morak.performancetracker.context.Accumulator
import com.morak.performancetracker.context.ResultMapper
import org.aopalliance.intercept.MethodInvocation
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.function.Executable
import org.mockito.BDDMockito
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(MockitoExtension::class)
internal class ConnectionAdviceTest {
    @Mock
    private val methodInvocation: MethodInvocation? = null

    @Test
    @Throws(Throwable::class)
    fun proxy된_객체가_반환되고_monitor에_query를_세팅한다() {
        // given
        BDDMockito.given(methodInvocation!!.proceed()).willReturn(Any())
        BDDMockito.given(methodInvocation.arguments).willReturn(arrayOf<Any>("insert into sql"))
        val queryMonitor = QueryMonitor()
        val advice = ConnectionAdvice(
            queryMonitor,
            Accumulator(ResultMapper())
        )

        // when
        val result = advice.invoke(methodInvocation)

        // then
        org.junit.jupiter.api.Assertions.assertAll(
            Executable { assertThat(result).isNotNull() },
            Executable { assertThat(queryMonitor.query).isEqualTo("insert into sql") }
        )
    }
}
