package com.morak.performancetracker

import com.morak.performancetracker.configuration.PerformanceConfiguration
import org.assertj.core.api.AssertionsForClassTypes.assertThatNoException
import org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.NoSuchBeanDefinitionException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest
import org.springframework.context.ApplicationContext

class PerformanceTrackerTest {
    @Nested
    @JdbcTest
    @PerformanceTracker
    inner class `Jdbc 테스트에서 PerformanceTracker 어노테이션이 있는 경우` {
        @Autowired
        private lateinit var context: ApplicationContext

        @Test
        fun `관련 빈이 등록된다`() {
            assertThatNoException().isThrownBy {
                context.getBean(PerformanceConfiguration::class.java)
            }
        }
    }

    @Nested
    @JdbcTest
    inner class `Jdbc 테스트에서 PerformanceTracker 어노테이션이 없는 경우` {
        @Autowired
        private lateinit var context: ApplicationContext

        @Test
        fun `관련 빈이 등록되지 않는다`() {
            assertThatThrownBy {
                context.getBean(PerformanceConfiguration::class.java)
            }.isInstanceOf(NoSuchBeanDefinitionException::class.java)
        }
    }
}
