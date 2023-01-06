package com.morak.performancetracker.aop.rest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

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
        boolean isProxy = AopUtils.isAopProxy(restTemplate);
        // then
        assertThat(isProxy).isTrue();
    }
}
