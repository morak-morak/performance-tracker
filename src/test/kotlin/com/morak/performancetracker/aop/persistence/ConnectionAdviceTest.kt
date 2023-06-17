package com.morak.performancetracker.aop.persistence

import com.morak.performancetracker.context.Accumulator
import com.morak.performancetracker.context.ResultMapper
import org.aopalliance.intercept.MethodInvocation
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.function.Executable
import org.mockito.BDDMockito.*
import org.mockito.Mockito
import org.mockito.junit.jupiter.MockitoExtension
import java.sql.Connection

@ExtendWith(MockitoExtension::class)
class ConnectionAdviceTest {

    @Test
    fun proxy된_객체가_반환되고_monitor에_query를_세팅한다() {
        // given
        val connection = Mockito.mock(Connection::class.java)
        val methodInvocation = Mockito.mock(MethodInvocation::class.java)
        given(methodInvocation.proceed()).willReturn(connection)
        given(methodInvocation.arguments).willReturn(arrayOf<Any>("insert into sql"))
        val queryMonitor = QueryMonitor()
        val advice = ConnectionAdvice(
            queryMonitor,
            Accumulator(ResultMapper())
        )

        // when
        val result = advice.invoke(methodInvocation)

        // then
        Assertions.assertAll(
            Executable { assertThat(result).isNotNull() },
            Executable { assertThat(queryMonitor.query).isEqualTo("insert into sql") }
        )
    }
}