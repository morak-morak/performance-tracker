package com.morak.performancetracker.aop;

import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

@Component
public class PerformanceMonitor {

    private static final double WARNING_REQUEST_TIME = 200 * 1_000_000.0;
    private static final double WARNING_QUERY_COUNT = 5;
    private static final double WARNING_QUERY_TIME = 100 * 1_000_000.0;

    private String uri;
    private String method;
    private double requestTime;
    private int queryCount;
    private double queryTime;
    private boolean activate = false;

    public void start(HttpServletRequest request) {
        uri = request.getRequestURI();
        method = request.getMethod();
        requestTime = System.nanoTime();
        activate = true;
    }

    public void increaseQueryCount() {
        if (activate) {
            queryCount++;
        }
    }

    public void addQueryTime(long queryTime) {
        if (activate) {
            this.queryTime += queryTime;
        }
    }

    public void end() {
        requestTime = System.nanoTime() - requestTime;
        activate = false;
    }

    public boolean isWarning() {
        return requestTime >= WARNING_REQUEST_TIME
                || queryCount >= WARNING_QUERY_COUNT || queryTime >= WARNING_QUERY_TIME;
    }

    private double convertNanoToMilli(double nano) {
        return nano / 1_000_000.0;
    }

    public String getUri() {
        return uri;
    }

    public String getMethod() {
        return method;
    }

    public double getRequestTime() {
        return requestTime;
    }

    public int getQueryCount() {
        return queryCount;
    }

    public double getQueryTime() {
        return queryTime;
    }

    public boolean isActivate() {
        return activate;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public void setRequestTime(double requestTime) {
        this.requestTime = requestTime;
    }

    public void setQueryCount(int queryCount) {
        this.queryCount = queryCount;
    }

    public void setQueryTime(double queryTime) {
        this.queryTime = queryTime;
    }

    public void setActivate(boolean activate) {
        this.activate = activate;
    }

    @Override
    public String toString() {
        return String.format(
                "uri: '%s', method: '%s', 요청 처리 시간: %f ms, 쿼리 개수: %d, 쿼리 시간: %f ms",
                uri,
                method,
                convertNanoToMilli(requestTime),
                queryCount,
                convertNanoToMilli(queryTime)
        );
    }
}
