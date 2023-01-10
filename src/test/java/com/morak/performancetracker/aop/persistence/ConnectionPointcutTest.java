package com.morak.performancetracker.aop.persistence;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import org.junit.jupiter.api.Test;

class ConnectionPointcutTest {

    @Test
    void preparedStatement가_포함된_메서드가_호출되면_true를_반환한다() throws NoSuchMethodException {
        // given
        ConnectionPointcut pointcut = new ConnectionPointcut();

        // when
        boolean result = pointcut.matches(Connection.class.getMethod("prepareStatement", String.class), null);

        // then
        assertThat(result).isTrue();
    }

    @Test
    void preparedStatement가_포함되지_않은_메서드가_호출되면_false를_반환한다() throws NoSuchMethodException {
        // given
        ConnectionPointcut pointcut = new ConnectionPointcut();

        // when
        boolean result = pointcut.matches(Connection.class.getMethod("getAutoCommit"), null);

        // then
        assertThat(result).isFalse();
    }
}
