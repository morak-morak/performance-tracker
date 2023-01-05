package com.morak.performancetracker.aop.rest;

import org.junit.jupiter.api.Test;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.web.client.RestTemplate;

@SpringBootTest
class RestMonitorAopTest {

    private final RestTemplateBuilder builder;

    @Autowired
    public RestMonitorAopTest(RestTemplateBuilder builder) {
        this.builder = builder;
    }

    @Test
    void 빌더로_RestTemplate_생성시_프록시를_반환한다() {
        // given
        RestTemplate restTemplate = builder.build();
        // when
        boolean aopProxy = AopUtils.isAopProxy(restTemplate);
        // then
        System.out.println("aopProxy = " + aopProxy);
    }
}
