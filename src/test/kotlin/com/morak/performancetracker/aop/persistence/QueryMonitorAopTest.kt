package com.morak.performancetracker.aop.persistence

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.lang.reflect.Proxy
import java.sql.Connection
import javax.sql.DataSource

@SpringBootTest
class QueryMonitorAopTest @Autowired constructor(private val dataSource: DataSource) {
    @Test
    fun 커넥션을_프록시로_만든다() {
        // given
        val proxyConnection = dataSource.connection
        // when
        val proxyClass: Class<out Connection> = proxyConnection.javaClass
        // then
        Assertions.assertThat(Proxy.isProxyClass(proxyClass)).isTrue()
    }
}
