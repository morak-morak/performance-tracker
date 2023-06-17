package com.morak.performancetracker.aop.persistence

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import java.sql.Connection

internal class ConnectionPointcutTest {
    @Test
    @Throws(NoSuchMethodException::class)
    fun preparedStatement가_포함된_메서드가_호출되면_true를_반환한다() {
        // given
        val pointcut = ConnectionPointcut()

        // when
        val result = pointcut.matches(Connection::class.java.getMethod("prepareStatement", String::class.java), null)

        // then
        Assertions.assertThat(result).isTrue()
    }

    @Test
    @Throws(NoSuchMethodException::class)
    fun preparedStatement가_포함되지_않은_메서드가_호출되면_false를_반환한다() {
        // given
        val pointcut = ConnectionPointcut()

        // when
        val result = pointcut.matches(Connection::class.java.getMethod("getAutoCommit"), null)

        // then
        Assertions.assertThat(result).isFalse()
    }
}
