package com.morak.performancetracker.description;

import com.morak.performancetracker.result.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class LoggingDescriptor implements Descriptor {

    private final Logger log = LoggerFactory.getLogger("PERFORMANCE");

    @Override
    public void describe(Result result) {
        log.info(result.getResult());
    }
}
