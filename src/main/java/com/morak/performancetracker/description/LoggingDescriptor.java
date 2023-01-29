package com.morak.performancetracker.description;

import com.morak.performancetracker.context.*;
import com.morak.performancetracker.utils.ListUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(value = "com.morak.performance-tracker.format", havingValue = "log", matchIfMissing = true)
public class LoggingDescriptor implements Descriptor {

    private final Logger log = LoggerFactory.getLogger("PERFORMANCE");

    @Override
    public void describe(Result root) {
        describeInternal(root, 0);
    }

    private void describeInternal(Result entry, int indent) {
        describeOnDepth(entry.getResult(), indent);
        if (ListUtils.isNullOrEmpty(entry.getSubResults())) {
            return;
        }
        for (Result subResult : entry.getSubResults()) {
            describeInternal(subResult, indent + 1);
        }
    }

    private void describeOnDepth(String message, int depth) {
        String prefix = " ".repeat((depth * 4));
        log.info(prefix + message);
    }
}
