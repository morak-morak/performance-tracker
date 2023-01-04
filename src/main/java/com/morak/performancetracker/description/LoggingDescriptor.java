package com.morak.performancetracker.description;

import com.morak.performancetracker.context.Context;
import com.morak.performancetracker.context.Result;
import com.morak.performancetracker.context.Scope;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class LoggingDescriptor implements Descriptor {

    private final Logger log = LoggerFactory.getLogger("PERFORMANCE");

    @Override
    public void describe(final String methodName) {
        log.info(methodName);
    }

    @Override
    public void describe(Result result) {
        log.info(result.getResult());
    }

    @Override
    public void describe(Context context) {
        for (Scope scope : context.getScopes()) {
            if (context.getName() == null) {
                describeScope(scope, -1);
                continue;
            }
            describeOnDepth(context.getName(), 0);
            describeScope(scope, 0);
        }
    }

    private void describeScope(Scope scope, int depth) {
        describeOnDepth(scope.getName(), depth + 1);
        for (Result result : scope.getSummaries()) {
            describeOnDepth(result.getResult(), depth + 2);
        }
    }

    private void describeOnDepth(String message, int depth) {
        String prefix = " ".repeat((depth * 4));
        log.info(prefix + message);
    }
}
