package com.morak.performancetracker.description;

import com.morak.performancetracker.context.Context;
import com.morak.performancetracker.context.Result;
import com.morak.performancetracker.context.Root;
import com.morak.performancetracker.context.Scope;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(value = "com.morak.performance-tracker.format", havingValue = "log", matchIfMissing = true)
public class LoggingDescriptor implements Descriptor {

    private final Logger log = LoggerFactory.getLogger("PERFORMANCE");

    @Override
    public void describe(Root root) {
        int depth = 0;
        for (Context context : root.getContexts()) {
            describeContext(context, depth);
        }
    }

    private void describeContext(Context context, int depth) {
        describeOnDepth(context.getName(), depth);
        for (Scope scope : context.getScopes()) {
            describeScope(scope, depth + 1);
        }
    }

    private void describeScope(Scope scope, int depth) {
        describeOnDepth(scope.getName(), depth);
        for (Result result : scope.getSummaries()) {
            describeOnDepth(result.getResult(), depth + 1);
        }
    }

    private void describeOnDepth(String message, int depth) {
        // todo : prettify prefix like tree (e.g. `brew install tree`)
        String prefix = " ".repeat(depth * 4);
        log.info(prefix + message);
    }
}
