package com.morak.performancetracker.aop.persistence

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import java.sql.PreparedStatement

class StatementPointcutTest {
    @Test
    fun `execute가 포함된 메서드가 호출되면 true를 반환한다`() {
        // given
        val pointcut = StatementPointcut()

        // when
        val result = pointcut.matches(PreparedStatement::class.java.getMethod("execute"), Any::class.java)

        // then
        Assertions.assertThat(result).isTrue()
    }

    @Test
    fun `execute가 포함되지 않은 메서드가 호출되면 false를 반환한다`() {
        // given
        val pointcut = StatementPointcut()

        // when
        val result = pointcut.matches(PreparedStatement::class.java.getMethod("getGeneratedKeys"), Any::class.java)

        // then
        Assertions.assertThat(result).isFalse()
    }
}
