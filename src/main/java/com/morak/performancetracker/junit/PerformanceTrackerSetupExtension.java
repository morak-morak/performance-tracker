package com.morak.performancetracker.junit;

import com.morak.performancetracker.Monitor;
import com.morak.performancetracker.aop.persistence.QueryMonitor;
import com.morak.performancetracker.aop.web.WebMonitor;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

public class PerformanceTrackerSetupExtension implements AfterEachCallback {

    private final Logger log = LoggerFactory.getLogger("PERFORMANCE");

    @Override
    public void afterEach(ExtensionContext context) {
        ApplicationContext applicationContext = SpringExtension.getApplicationContext(context);
        Optional<Throwable> executionException = context.getExecutionException();
        if (executionException.isPresent()) {
            log.warn("fails on test execution");
            return;
        }
        QueryMonitor monitor = applicationContext.getBean(QueryMonitor.class);
        WebMonitor webMonitor = applicationContext.getBean(WebMonitor.class);

        Map<String, Monitor> beansOfType = applicationContext.getBeansOfType(Monitor.class);
        for (Entry<String, Monitor> entry : beansOfType.entrySet()) {
            log.info(entry.getValue().getResult());
        }

        monitor.clear();
        webMonitor.clear();
    }
}
