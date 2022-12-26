package com.morak.performancetracker.aop.persistence;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.PreparedStatement;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ProxyConnectionHandlerTest {

    @Mock
    private Connection connection;

    @Mock
    private PreparedStatement statement;

    @Test
    void Proxy_Statement를_반환한다() throws Throwable {
        // given
        given(connection.prepareStatement(any())).willReturn(statement);
        QueryMonitor monitor = new QueryMonitor();
        ProxyConnectionHandler handler = new ProxyConnectionHandler(connection, monitor);
        // when
        Object result = handler.invoke(
                connection,
                connection.getClass().getMethod("prepareStatement", String.class),
                new Object[]{"hiyo"}
        );
        // then
        assertThat(Proxy.isProxyClass(result.getClass())).isTrue();
    }

    @Test
    void Statment가_포함되지_않으면_일반_객체를_반환한다() throws Throwable {
        // given
        given(connection.getAutoCommit()).willReturn(true);
        QueryMonitor monitor = new QueryMonitor();
        ProxyConnectionHandler handler = new ProxyConnectionHandler(connection, monitor);
        // when
        Object result = handler.invoke(
                connection,
                connection.getClass().getMethod("getAutoCommit"),
                null
        );
        // then
        assertThat(Proxy.isProxyClass(result.getClass())).isFalse();
    }
}
