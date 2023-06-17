package com.morak.performancetracker.description

import ch.qos.logback.classic.Logger
import ch.qos.logback.classic.spi.ILoggingEvent
import ch.qos.logback.core.read.ListAppender
import com.morak.performancetracker.context.Context
import com.morak.performancetracker.context.Result
import com.morak.performancetracker.context.Root
import com.morak.performancetracker.context.Scope
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.TestPropertySource

@SpringBootTest
class DescriptorTest {
    @Nested
    inner class `Properties에 format이 없는 경우` {

        @Autowired
        private lateinit var descriptor: Descriptor
        private lateinit var logWatcher: ListAppender<ILoggingEvent>

        @BeforeEach
        fun setUp() {
            logWatcher = ListAppender()
            (LoggerFactory.getLogger("PERFORMANCE") as Logger).addAppender(logWatcher)
            logWatcher.start()
        }

        @Test
        fun `LoggingDescriptor로 빈이 생성된다`() {
            assertThat(descriptor.javaClass).isEqualTo(LoggingDescriptor::class.java)
        }

        @Test
        fun `LogginDescriptor는 로깅으로 출력된다`() {
            //given
            val context = Context(
                "firstClass", listOf(Scope("firstMethod", listOf(Result("firstQuery", 2.0))))
            )
            //when
            descriptor.describe(Root(listOf(context)))
            //then
            val loggingEvents = logWatcher.list
            assertAll({ assertThat(loggingEvents).hasSize(3) },
                { assertThat(loggingEvents[0].message).contains("firstClass") },
                { assertThat(loggingEvents[1].message).contains("firstMethod") },
                { assertThat(loggingEvents[2].message).contains("firstQuery") })
        }
    }

    @Nested
    @TestPropertySource(properties = ["com.morak.performance-tracker.format = json"])
    inner class `Properties에 format이 있는 경우` {
        @Autowired
        private lateinit var descriptor: Descriptor

        @Test
        fun `JsonDescriptor로 빈이 생성된다`() {
            assertThat(descriptor.javaClass).isEqualTo(JsonDescriptor::class.java)
        }
    }
}
