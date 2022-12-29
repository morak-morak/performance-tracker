package com.morak.performancetracker;

import static org.assertj.core.api.Assertions.assertThat;

import com.morak.performancetracker.aop.persistence.QueryMonitor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

@SpringBootTest
@PerformanceTracker
@Sql("classpath:schema.sql")
public class DatabaseInitializationTest {

    private final QueryMonitor queryMonitor;

    @Autowired
    public DatabaseInitializationTest(QueryMonitor queryMonitor) {
        this.queryMonitor = queryMonitor;
    }

    @Test
    void SQL파일에_의한_쿼리는_측정하지_않는다() {
        assertThat(queryMonitor.getQueryCount()).isZero();
    }
}
