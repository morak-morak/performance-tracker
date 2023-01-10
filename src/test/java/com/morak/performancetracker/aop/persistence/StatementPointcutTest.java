package com.morak.performancetracker.aop.persistence;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.PreparedStatement;
import org.junit.jupiter.api.Test;

class StatementPointcutTest {

    @Test
    void execute가_포함된_메서드가_호출되면_true를_반환한다() throws NoSuchMethodException {
        // given
        StatementPointcut pointcut = new StatementPointcut();

        // when
        boolean result = pointcut.matches(PreparedStatement.class.getMethod("execute"), null);

        // then
        assertThat(result).isTrue();
    }

    @Test
    void execute가_포함되지_않은_메서드가_호출되면_false를_반환한다() throws NoSuchMethodException {
        // given
        StatementPointcut pointcut = new StatementPointcut();

        // when
        boolean result = pointcut.matches(PreparedStatement.class.getMethod("getGeneratedKeys"), null);

        // then
        assertThat(result).isFalse();
    }
}
