package com.morak.performancetracker.description;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import com.morak.performancetracker.context.Context;
import com.morak.performancetracker.context.Result;
import com.morak.performancetracker.context.Scope;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
class DescriptorTest {

    @Nested
    class Properties에_format이_없는_경우 {

        @Autowired
        private ApplicationContext context;

        private ListAppender<ILoggingEvent> logWatcher;
        private Descriptor descriptor;

        @BeforeEach
        void setUp() {
            descriptor = context.getBean(Descriptor.class);
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
            Context context = new Context("firstClass",
                    List.of(new Scope("firstMethod", List.of(new Result("firstQuery", 2.0)))));
            //when
            descriptor.describe(context);
            //then
            List<ILoggingEvent> loggingEvents = logWatcher.list;
            assertAll(
                    () -> assertThat(loggingEvents).hasSize(3),
                    () -> assertThat(loggingEvents.get(0).getMessage()).isEqualTo("firstClass"),
                    () -> assertThat(loggingEvents.get(1).getMessage()).isEqualTo("    firstMethod"),
                    () -> assertThat(loggingEvents.get(2).getMessage()).isEqualTo("        Result{name='firstQuery', elapsed=2.0}")
            );
        }
    }

    @Nested
    @TestPropertySource(properties = "com.morak.performance-tracker.format = json")
    class Properties에_format이_있는_경우 {

        @AfterEach
        void teardown() {

        }

        @Autowired
        private ApplicationContext context;

        @Test
        void JsonDescriptor로_빈이_생성된다() {
            Descriptor bean = context.getBean(Descriptor.class);

            assertThat(bean.getClass())
                    .isEqualTo(JsonDescriptor.class);
        }
    }
}
