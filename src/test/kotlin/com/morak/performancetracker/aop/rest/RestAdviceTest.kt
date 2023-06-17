package com.morak.performancetracker.aop.rest


import com.morak.performancetracker.context.Accumulator
import com.morak.performancetracker.context.ResultMapper
import org.aopalliance.intercept.MethodInvocation
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.AssertionsForClassTypes
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.function.Executable
import org.mockito.BDDMockito
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(MockitoExtension::class)
class RestAdviceTest {
    @Mock
    private val methodInvocation: MethodInvocation? = null

    @Test
    fun REST_요청_시간을_측정한다() {
        // given
        BDDMockito.given(methodInvocation!!.arguments).willReturn(arrayOf<Any>("https://example.com"))
        BDDMockito.given(methodInvocation.proceed()).willReturn(Any())
        val accumulator = Accumulator(ResultMapper())
        val advice = RestAdvice(RestMonitor(), accumulator)

        // when
        val result = advice.invoke(methodInvocation)
        // then
        Assertions.assertAll(
            Executable { assertThat(accumulator.results.size).isOne() },
            Executable { AssertionsForClassTypes.assertThat(result).isNotNull() }
        )
    }
}
