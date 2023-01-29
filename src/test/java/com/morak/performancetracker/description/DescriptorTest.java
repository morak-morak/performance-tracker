package com.morak.performancetracker.description;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import com.morak.performancetracker.context.Result;
import com.morak.performancetracker.context.ResultComposite;
import com.morak.performancetracker.context.ResultLeaf;
import com.morak.performancetracker.context.TestMetadata;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@SpringBootTest
class DescriptorTest {

    @Nested
    class Properties에_format이_없는_경우 {

        @Autowired
        private Descriptor descriptor;

        private ListAppender<ILoggingEvent> logWatcher;

        @BeforeEach
        void setUp() {
            logWatcher = new ListAppender<>();
            ((Logger) LoggerFactory.getLogger("PERFORMANCE")).addAppender(logWatcher);
            logWatcher.start();
        }

        @Test
        void LoggingDescriptor로_빈이_생성된다() {
            assertThat(descriptor.getClass())
                    .isEqualTo(LoggingDescriptor.class);
        }

        @Test
        void LogginDescriptor는_로깅으로_출력된다() {
            //given
            Result context = new ResultComposite(
                    new TestMetadata("firstClass", ""),
                    List.of(new ResultComposite(
                                    new TestMetadata("firstMethod", ""), List.of(
                                    new ResultLeaf("firstQuery", 2.0))
                            )
                    )
            );
            //when
            descriptor.describe(context);
            //then
            List<ILoggingEvent> loggingEvents = logWatcher.list;
            assertAll(
                    () -> assertThat(loggingEvents).hasSize(3),
                    () -> assertThat(loggingEvents.get(0).getMessage()).contains("firstClass"),
                    () -> assertThat(loggingEvents.get(1).getMessage()).contains("firstMethod"),
                    () -> assertThat(loggingEvents.get(2).getMessage()).contains("firstQuery")
            );
        }
    }

    @Nested
    @TestPropertySource(properties = "com.morak.performance-tracker.format = json")
    class Properties에_format이_있는_경우 {

        @Autowired
        private Descriptor descriptor;

        @Test
        void JsonDescriptor로_빈이_생성된다() {
            assertThat(descriptor.getClass())
                    .isEqualTo(JsonDescriptor.class);
        }
    }
}
