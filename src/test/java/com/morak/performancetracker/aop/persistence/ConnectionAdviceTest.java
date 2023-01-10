package com.morak.performancetracker.aop.persistence;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

import com.morak.performancetracker.context.Accumulator;
import com.morak.performancetracker.context.ResultMapper;
import org.aopalliance.intercept.MethodInvocation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ConnectionAdviceTest {

    @Mock
    private MethodInvocation methodInvocation;

    @Test
    void proxy된_객체가_반환되고_monitor에_query를_세팅한다() throws Throwable {
        // given
        given(methodInvocation.proceed()).willReturn(new Object());
        given(methodInvocation.getArguments()).willReturn(new Object[]{"insert into sql"});
        QueryMonitor queryMonitor = new QueryMonitor();
        ConnectionAdvice advice = new ConnectionAdvice(queryMonitor,
                new Accumulator(new ResultMapper()));

        // when
        Object result = advice.invoke(methodInvocation);

        // then
        Assertions.assertAll(
                () -> assertThat(result).isNotNull(),
                () -> assertThat(queryMonitor.getQuery()).isEqualTo("insert into sql")
        );
    }
}
