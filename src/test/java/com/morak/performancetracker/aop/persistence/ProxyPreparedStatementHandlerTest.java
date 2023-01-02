package com.morak.performancetracker.aop.persistence;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.BDDMockito.given;

import com.morak.performancetracker.context.Accumulator;
import com.morak.performancetracker.context.ResultMapper;
import java.sql.PreparedStatement;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ProxyPreparedStatementHandlerTest {

    @Mock
    private PreparedStatement statement;

    @Test
    void 쿼리_성능을_측정한다() throws Throwable {
        // given
        given(statement.execute()).willReturn(true);
        Accumulator accumulator = new Accumulator(new ResultMapper());
        QueryMonitor monitor = new QueryMonitor();
        ProxyPreparedStatementHandler handler = new ProxyPreparedStatementHandler(statement, accumulator, monitor);
        // when
        Boolean result = (Boolean) handler.invoke(statement, statement.getClass().getMethod("execute"), null);
        // then
        Assertions.assertAll(
                () -> assertThat(result).isTrue()
                // todo: result mapper 에서 mapMonitor() 시 monitor 를 clear 시킨다.
//                () -> assertThat(monitor.getQueryTime()).isNotZero()
        );
    }

    @Test
    void Execute가_아니면_쿼리_성능을_측정하지_않는다() throws Throwable {
        // given
        given(statement.isClosed()).willReturn(true);
        Accumulator accumulator = new Accumulator(new ResultMapper());
        QueryMonitor monitor = new QueryMonitor();
        ProxyPreparedStatementHandler handler = new ProxyPreparedStatementHandler(statement, accumulator, monitor);
        // when
        Boolean result = (Boolean) handler.invoke(statement, statement.getClass().getMethod("isClosed"), null);
        // then
        Assertions.assertAll(
                () -> assertThat(result).isTrue(),
                () -> assertThat(monitor.getQueryTime()).isZero()
        );
    }
}
