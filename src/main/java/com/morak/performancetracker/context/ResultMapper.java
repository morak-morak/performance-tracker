package com.morak.performancetracker.context;

import com.morak.performancetracker.Monitor;
import com.morak.performancetracker.aop.persistence.QueryMonitor;
import com.morak.performancetracker.aop.web.WebMonitor;
import org.springframework.stereotype.Component;

@Component
public class ResultMapper {

    public Result mapMonitor(Monitor monitor) {
        Result result = mapToResult(monitor);
        monitor.clear();
        return result;
    }

    private Result mapToResult(Monitor monitor) {
        if (monitor instanceof QueryMonitor) {
            QueryMonitor qm = (QueryMonitor) monitor;
            return new Result(qm.getQuery(), qm.getQueryTime());
        }
        if (monitor instanceof WebMonitor) {
            WebMonitor wm = (WebMonitor) monitor;
            return new Result(wm.getMethod() + " " + wm.getUri(), wm.getElapsed());
        }
        throw new IllegalArgumentException("Couldn't find proper monitor");
    }
}
