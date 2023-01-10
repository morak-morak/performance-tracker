package com.morak.performancetracker.aop.persistence;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import com.morak.performancetracker.context.Accumulator;
import com.morak.performancetracker.context.ResultMapper;
import org.aopalliance.intercept.MethodInvocation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class StatementAdviceTest {

    @Mock
    private MethodInvocation methodInvocation;

    @Test
    void accumulator에_result가_쌓인다() throws Throwable {
        // given
        QueryMonitor queryMonitor = new QueryMonitor();
        Accumulator accumulator = new Accumulator(new ResultMapper());
        StatementAdvice advice = new StatementAdvice(queryMonitor, accumulator);

        // when
        Object result = advice.invoke(methodInvocation);

        // then
        assertThat(accumulator.getResults().get(queryMonitor.getSignature()).size()).isOne();
    }
}
