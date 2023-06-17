package com.morak.performancetracker.aop.rest


import com.morak.performancetracker.context.Accumulator
import com.morak.performancetracker.context.ResultMapper
import org.aopalliance.intercept.MethodInvocation
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.AssertionsForClassTypes
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.given
import org.mockito.Mockito

class RestAdviceTest {

    @Test
    fun REST_요청_시간을_측정한다() {
        // given
        val methodInvocation = Mockito.mock(MethodInvocation::class.java)
        given(methodInvocation.arguments).willReturn(arrayOf<Any>("https://example.com"))
        given(methodInvocation.proceed()).willReturn(Any())
        val accumulator = Accumulator(ResultMapper())
        val advice = RestAdvice(RestMonitor(), accumulator)

        // when
        val result = advice.invoke(methodInvocation)
        // then
        Assertions.assertAll(
            { assertThat(accumulator.results.size).isOne() },
            { AssertionsForClassTypes.assertThat(result).isNotNull() }
        )
    }
}
