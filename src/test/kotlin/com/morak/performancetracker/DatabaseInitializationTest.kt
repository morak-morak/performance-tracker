package com.morak.performancetracker

import com.morak.performancetracker.aop.persistence.QueryMonitor
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.TestConstructor
import org.springframework.test.context.jdbc.Sql

@SpringBootTest
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@PerformanceTracker
@Sql("classpath:schema.sql")
class DatabaseInitializationTest(private val queryMonitor: QueryMonitor) {
    @Test
    fun SQL파일에_의한_쿼리는_측정하지_않는다() {
        assertThat(queryMonitor.query).isNull()
    }
}
