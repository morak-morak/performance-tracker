package com.morak.performancetracker;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatNoException;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import com.morak.performancetracker.configuration.PerformanceConfiguration;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.ApplicationContext;

public class PerformanceTrackerTest {

    @Nested
    @JdbcTest
    @PerformanceTracker
    class Jdbc_테스트에서_PerformanceTracker_어노테이션이_있는_경우 {
        @Autowired
        private ApplicationContext context;

        @Test
        void 관련_빈이_등록된다() {
            assertThatNoException().isThrownBy(() -> context.getBean(PerformanceConfiguration.class));
        }
    }

    @Nested
    @JdbcTest
    class Jdbc_테스트에서_PerformanceTracker_어노테이션이_없는_경우 {
        @Autowired
        private ApplicationContext context;

        @Test
        void 관련_빈이_등록되지_않는다() {
            assertThatThrownBy(() -> context.getBean(PerformanceConfiguration.class))
                    .isInstanceOf(NoSuchBeanDefinitionException.class);
        }
    }
}
