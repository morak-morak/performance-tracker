package com.morak.performancetracker.description;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import com.morak.performancetracker.ContextType;
import com.morak.performancetracker.context.Context;
import com.morak.performancetracker.context.Result;
import com.morak.performancetracker.context.Root;
import com.morak.performancetracker.context.Scope;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

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
        void LoggingDescriptor는_로깅으로_출력된다() {
            //given
            Context context = new Context("firstClass",
                    List.of(new Scope("firstMethod", List.of(new Result("firstQuery", 2.0)))));
            //when
            descriptor.describe(new Root(List.of(context)), ContextType.METHOD);
            //then
            String logMessages = logWatcher.list.stream()
                    .map(ILoggingEvent::getMessage)
                    .collect(Collectors.joining("\n"));
            assertThat(logMessages).contains("firstClass", "firstMethod", "firstQuery");
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
