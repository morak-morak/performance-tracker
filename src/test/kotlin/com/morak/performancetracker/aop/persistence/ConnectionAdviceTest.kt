package com.morak.performancetracker.aop.persistence

import com.morak.performancetracker.context.Accumulator
import com.morak.performancetracker.context.ResultMapper
import org.aopalliance.intercept.MethodInvocation
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.given
import org.mockito.Mockito
import java.sql.Connection

class ConnectionAdviceTest {

    @Test
    fun `proxy된 객체가 반환되고 monitor에 query를 세팅한다`() {
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
            { assertThat(result).isNotNull() },
            { assertThat(queryMonitor.query).isEqualTo("insert into sql") }
        )
    }
}
