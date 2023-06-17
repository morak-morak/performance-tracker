package com.morak.performancetracker

import com.morak.performancetracker.aop.persistence.QueryMonitor
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.jdbc.Sql

@SpringBootTest
@PerformanceTracker
@Sql("classpath:schema.sql")
class DatabaseInitializationTest @Autowired constructor(private val queryMonitor: QueryMonitor) {
    @Test
    fun SQL파일에_의한_쿼리는_측정하지_않는다() {
        assertThat(queryMonitor.query).isNull()
    }
}
