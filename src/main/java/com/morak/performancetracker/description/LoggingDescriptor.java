package com.morak.performancetracker.description;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class LoggingDescriptor implements Descriptor {

    private final Logger log = LoggerFactory.getLogger("PERFORMANCE");

    @Override
    public void describe(String result) {
        log.info(result);
    }
}
