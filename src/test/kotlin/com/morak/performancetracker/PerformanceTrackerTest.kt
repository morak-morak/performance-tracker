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
    inner class Jdbc_테스트에서_PerformanceTracker_어노테이션이_있는_경우 {
        @Autowired
        private lateinit var context: ApplicationContext

        @Test
        fun 관련_빈이_등록된다() {
            assertThatNoException().isThrownBy {
                context.getBean(PerformanceConfiguration::class.java)
            }
        }
    }

    @Nested
    @JdbcTest
    inner class Jdbc_테스트에서_PerformanceTracker_어노테이션이_없는_경우 {
        @Autowired
        private lateinit var context: ApplicationContext

        @Test
        fun 관련_빈이_등록되지_않는다() {
            assertThatThrownBy {
                context.getBean(PerformanceConfiguration::class.java)
            }.isInstanceOf(NoSuchBeanDefinitionException::class.java)
        }
    }
}
