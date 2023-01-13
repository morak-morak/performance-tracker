package com.morak.performancetracker;

import static org.assertj.core.api.Assertions.assertThat;

import com.morak.performancetracker.context.ContextManager;
import com.morak.performancetracker.context.EndpointContextManager;
import com.morak.performancetracker.context.MethodContextManager;
import java.util.List;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@PerformanceTracker
public class ContextTypePropertiesTest {

    @Nested
    class Context_Type에_아무것도_없는경우 {

        @Autowired
        private List<ContextManager> managers;

        @Test
        void 모든_Type이_등록된다() {
            assertThat(managers).hasOnlyElementsOfTypes(MethodContextManager.class, EndpointContextManager.class);
        }
    }

    @Nested
    @TestPropertySource(properties = "com.morak.performance-tracker.context.type = method")
    class Context_Type에_Method만_있는_경우 {

        @Autowired
        private List<ContextManager> managers;

        @Test
        void Method_타입만_등록된다() {
            assertThat(managers).hasExactlyElementsOfTypes(MethodContextManager.class);
        }
    }

    @Nested
    @TestPropertySource(properties = "com.morak.performance-tracker.context.type = endpoint")
    class Context_Type에_Endpoint만_있는_경우 {

        @Autowired
        private List<ContextManager> managers;

        @Test
        void Endpoint_타입만_등록된다() {
            assertThat(managers).hasExactlyElementsOfTypes(EndpointContextManager.class);
        }
    }

    @Nested
    @TestPropertySource(properties = "com.morak.performance-tracker.context.type = method, endpoint")
    class Context_Type에_여러개를_설정하는_경우 {

        @Autowired
        private List<ContextManager> managers;

        @Test
        void 설정한_모든_타입이_등록된다() {
            assertThat(managers).hasOnlyElementsOfTypes(MethodContextManager.class, EndpointContextManager.class);
        }
    }
}
