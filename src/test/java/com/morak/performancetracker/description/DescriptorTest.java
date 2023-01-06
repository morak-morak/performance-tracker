package com.morak.performancetracker.description;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
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

        @Test
        void LoggingDescriptor로_빈이_생성된다() {
            Descriptor bean = context.getBean(Descriptor.class);

            assertThat(bean.getClass())
                    .isEqualTo(LoggingDescriptor.class);
        }
    }

    @Nested
    @TestPropertySource(properties = "format = json")
    class Properties에_format이_있는_경우 {

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