package com.morak.performancetracker;

import com.morak.performancetracker.aop.configuration.PerformanceConfiguration;
import com.morak.performancetracker.junit.PerformanceTrackerSetupExtension;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.context.annotation.Import;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@ExtendWith(PerformanceTrackerSetupExtension.class)
@Import({PerformanceConfiguration.class})

public @interface PerformanceTracker {
}
