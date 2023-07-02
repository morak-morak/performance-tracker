package com.morak.performancetracker

import com.morak.performancetracker.context.ContextManager
import com.morak.performancetracker.context.EndpointContextManager
import com.morak.performancetracker.context.MethodContextManager
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.TestPropertySource

@SpringBootTest
@PerformanceTracker
class ContextTypePropertiesTest {
    @Nested
    inner class Context_Type에_아무것도_없는경우 {
        @Autowired
        private lateinit var managers: List<ContextManager>

        @Test
        fun 모든_Type이_등록된다() {
            assertThat(managers).hasOnlyElementsOfTypes(
                MethodContextManager::class.java,
                EndpointContextManager::class.java
            )
        }
    }

    @Nested
    @TestPropertySource(properties = ["com.morak.performance-tracker.context.type = method"])
    inner class Context_Type에_Method만_있는_경우 {
        @Autowired
        private lateinit var managers: List<ContextManager>

        @Test
        fun Method_타입만_등록된다() {
            assertThat(managers).hasExactlyElementsOfTypes(MethodContextManager::class.java)
        }
    }

    @Nested
    @TestPropertySource(properties = ["com.morak.performance-tracker.context.type = endpoint"])
    inner class Context_Type에_Endpoint만_있는_경우 {
        @Autowired
        private lateinit var managers: List<ContextManager>

        @Test
        fun Endpoint_타입만_등록된다() {
            assertThat(managers).hasExactlyElementsOfTypes(EndpointContextManager::class.java)
        }
    }

    @Nested
    @TestPropertySource(properties = ["com.morak.performance-tracker.context.type = method, endpoint"])
    internal inner class Context_Type에_여러개를_설정하는_경우 {
        @Autowired
        private lateinit var managers: List<ContextManager>
        @Test
        fun 설정한_모든_타입이_등록된다() {
            println("managers = ${managers}")
            assertThat(managers).hasOnlyElementsOfTypes(
                MethodContextManager::class.java,
                EndpointContextManager::class.java
            )
        }
    }
}