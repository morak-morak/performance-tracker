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
    inner class `Context Type에 아무것도 없는경우` {
        @Autowired
        private lateinit var managers: List<ContextManager>

        @Test
        fun `모든 Type이 등록된다`() {
            assertThat(managers).hasOnlyElementsOfTypes(
                MethodContextManager::class.java,
                EndpointContextManager::class.java
            )
        }
    }

    @Nested
    @TestPropertySource(properties = ["com.morak.performance-tracker.context.type = method"])
    inner class `Context Type에 Method만 있는 경우` {
        @Autowired
        private lateinit var managers: List<ContextManager>

        @Test
        fun `Method 타입만 등록된다`() {
            assertThat(managers).hasExactlyElementsOfTypes(MethodContextManager::class.java)
        }
    }

    @Nested
    @TestPropertySource(properties = ["com.morak.performance-tracker.context.type = endpoint"])
    inner class `ContextType에 Endpoint만 있는 경우` {
        @Autowired
        private lateinit var managers: List<ContextManager>

        @Test
        fun `Endpoint 타입만 등록된다`() {
            assertThat(managers).hasExactlyElementsOfTypes(EndpointContextManager::class.java)
        }
    }

    @Nested
    @TestPropertySource(properties = ["com.morak.performance-tracker.context.type = method, endpoint"])
    inner class `ContextType에 여러개를 설정하는 경우` {
        @Autowired
        private lateinit var managers: List<ContextManager>
        @Test
        fun `설정한 모든 타입이 등록된다`() {
            println("managers = ${managers}")
            assertThat(managers).hasOnlyElementsOfTypes(
                MethodContextManager::class.java,
                EndpointContextManager::class.java
            )
        }
    }
}