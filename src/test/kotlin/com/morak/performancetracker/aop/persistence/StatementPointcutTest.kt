package com.morak.performancetracker.aop.persistence

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import java.sql.PreparedStatement

class StatementPointcutTest {
    @Test
    fun execute가_포함된_메서드가_호출되면_true를_반환한다() {
        // given
        val pointcut = StatementPointcut()

        // when
        val result = pointcut.matches(PreparedStatement::class.java.getMethod("execute"), Any::class.java)

        // then
        Assertions.assertThat(result).isTrue()
    }

    @Test
    fun execute가_포함되지_않은_메서드가_호출되면_false를_반환한다() {
        // given
        val pointcut = StatementPointcut()

        // when
        val result = pointcut.matches(PreparedStatement::class.java.getMethod("getGeneratedKeys"), Any::class.java)

        // then
        Assertions.assertThat(result).isFalse()
    }
}
