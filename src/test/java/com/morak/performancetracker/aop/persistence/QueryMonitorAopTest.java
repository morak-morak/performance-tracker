package com.morak.performancetracker.aop.persistence;

import static org.assertj.core.api.Assertions.assertThat;

import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class QueryMonitorAopTest {

    private final DataSource dataSource;

    @Autowired
    public QueryMonitorAopTest(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Test
    void 커넥션을_프록시로_만든다() throws SQLException {
        // given
        Connection proxyConnection = dataSource.getConnection();
        // when
        Class<? extends Connection> proxyClass = proxyConnection.getClass();
        // then
        assertThat(Proxy.isProxyClass(proxyClass)).isTrue();
    }
}
