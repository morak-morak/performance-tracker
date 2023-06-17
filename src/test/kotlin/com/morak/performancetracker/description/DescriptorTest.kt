package com.morak.performancetracker.description

import ch.qos.logback.classic.Logger
import ch.qos.logback.classic.spi.ILoggingEvent
import ch.qos.logback.core.read.ListAppender
import com.morak.performancetracker.context.Context
import com.morak.performancetracker.context.Result
import com.morak.performancetracker.context.Root
import com.morak.performancetracker.context.Scope
import com.morak.performancetracker.description.JsonDescriptor
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.function.Executable
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.TestPropertySource
import java.util.List

@SpringBootTest
internal class DescriptorTest {
    @Nested
    internal inner class Properties에_format이_없는_경우 {
        @Autowired
        private val descriptor: Descriptor? = null
        private var logWatcher: ListAppender<ILoggingEvent>? = null
        @BeforeEach
        fun setUp() {
            logWatcher = ListAppender()
            (LoggerFactory.getLogger("PERFORMANCE") as Logger).addAppender(logWatcher)
            logWatcher!!.start()
        }

        @Test
        fun LoggingDescriptor로_빈이_생성된다() {
            Assertions.assertThat(descriptor!!.javaClass)
                .isEqualTo(LoggingDescriptor::class.java)
        }

        @Test
        fun LogginDescriptor는_로깅으로_출력된다() {
            //given
            val context = Context(
                "firstClass",
                List.of(Scope("firstMethod", List.of(Result("firstQuery", 2.0))))
            )
            //when
            descriptor!!.describe(Root(List.of(context)))
            //then
            val loggingEvents = logWatcher!!.list
            org.junit.jupiter.api.Assertions.assertAll(
                Executable { Assertions.assertThat(loggingEvents).hasSize(3) },
                Executable { Assertions.assertThat(loggingEvents[0].message).contains("firstClass") },
                Executable { Assertions.assertThat(loggingEvents[1].message).contains("firstMethod") },
                Executable { Assertions.assertThat(loggingEvents[2].message).contains("firstQuery") }
            )
        }
    }

    @Nested
    @TestPropertySource(properties = ["com.morak.performance-tracker.format = json"])
    internal inner class Properties에_format이_있는_경우 {
        @Autowired
        private val descriptor: Descriptor? = null
        @Test
        fun JsonDescriptor로_빈이_생성된다() {
            Assertions.assertThat(descriptor!!.javaClass)
                .isEqualTo(JsonDescriptor::class.java)
        }
    }
}
