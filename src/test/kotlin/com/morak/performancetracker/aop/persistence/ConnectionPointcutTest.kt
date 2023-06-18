package com.morak.performancetracker.aop.persistence

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import java.sql.Connection

class ConnectionPointcutTest {
    @Test
    fun `preparedStatement가 포함된 메서드가 호출되면 true를 반환한다`() {
        // given
        val pointcut = ConnectionPointcut()

        // when
        val result =
            pointcut.matches(Connection::class.java.getMethod("prepareStatement", String::class.java), Any::class.java)

        // then
        Assertions.assertThat(result).isTrue()
    }

    @Test
    fun `preparedStatement가 포함되지 않은 메서드가 호출되면 false를 반환한다`() {
        // given
        val pointcut = ConnectionPointcut()

        // when
        val result = pointcut.matches(Connection::class.java.getMethod("getAutoCommit"), Any::class.java)

        // then
        Assertions.assertThat(result).isFalse()
    }
}
