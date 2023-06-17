package com.morak.performancetracker.aop.persistence

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import java.sql.PreparedStatement

internal class StatementPointcutTest {
    @Test
    @Throws(NoSuchMethodException::class)
    fun execute가_포함된_메서드가_호출되면_true를_반환한다() {
        // given
        val pointcut = StatementPointcut()

        // when
        val result = pointcut.matches(PreparedStatement::class.java.getMethod("execute"), null)

        // then
        Assertions.assertThat(result).isTrue()
    }

    @Test
    @Throws(NoSuchMethodException::class)
    fun execute가_포함되지_않은_메서드가_호출되면_false를_반환한다() {
        // given
        val pointcut = StatementPointcut()

        // when
        val result = pointcut.matches(PreparedStatement::class.java.getMethod("getGeneratedKeys"), null)

        // then
        Assertions.assertThat(result).isFalse()
    }
}
