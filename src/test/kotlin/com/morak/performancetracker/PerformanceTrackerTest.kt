package com.morak.performancetracker

import com.morak.performancetracker.configuration.PerformanceConfiguration
import org.assertj.core.api.AssertionsForClassTypes
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
    internal inner class Jdbc_테스트에서_PerformanceTracker_어노테이션이_있는_경우 {
        @Autowired
        private val context: ApplicationContext? = null
        @Test
        fun 관련_빈이_등록된다() {
            AssertionsForClassTypes.assertThatNoException().isThrownBy {
                context!!.getBean(
                    PerformanceConfiguration::class.java
                )
            }
        }
    }

    @Nested
    @JdbcTest
    internal inner class Jdbc_테스트에서_PerformanceTracker_어노테이션이_없는_경우 {
        @Autowired
        private val context: ApplicationContext? = null
        @Test
        fun 관련_빈이_등록되지_않는다() {
            AssertionsForClassTypes.assertThatThrownBy {
                context!!.getBean(
                    PerformanceConfiguration::class.java
                )
            }
                .isInstanceOf(NoSuchBeanDefinitionException::class.java)
        }
    }
}
