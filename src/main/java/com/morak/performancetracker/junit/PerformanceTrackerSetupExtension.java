package com.morak.performancetracker.junit;

import com.morak.performancetracker.aop.persistence.QueryMonitor;
import com.morak.performancetracker.aop.persistence.PerformanceMonitorAop;
import com.morak.performancetracker.aop.web.WebMonitor;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

public class PerformanceTrackerSetupExtension implements BeforeEachCallback, AfterEachCallback {

    private final Logger log = LoggerFactory.getLogger("PERFORMANCE");

    @Override
    public void beforeEach(ExtensionContext context) {
        ApplicationContext applicationContext = SpringExtension.getApplicationContext(context);
        PerformanceMonitorAop aop = applicationContext.getBean(PerformanceMonitorAop.class);
        aop.setFlag();
    }

    @Override
    public void afterEach(ExtensionContext context) {
        ApplicationContext applicationContext = SpringExtension.getApplicationContext(context);
        QueryMonitor monitor = applicationContext.getBean(QueryMonitor.class);
        WebMonitor webMonitor = applicationContext.getBean(WebMonitor.class);

        log.info(monitor.toString());
        log.info(webMonitor.toString());
    }
}
