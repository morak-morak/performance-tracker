package com.morak.performancetracker.aop.rest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.lang.reflect.Type;
import java.net.URI;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestTemplate;

class RestPointcutTest {

    @Test
    void 메소드_이름이_일치하면_true를_반환한다() throws NoSuchMethodException {
        // given
        RestPointcut pointcut = new RestPointcut();
        // when
        boolean isMatch = pointcut.matches(RestTemplate.class.getMethod(
                "execute",
                URI.class,
                HttpMethod.class,
                RequestCallback.class,
                ResponseExtractor.class
        ), null);
        // then
        assertThat(isMatch).isTrue();
    }

    @Test
    void 메소드_이름이_일치하지_않으면_false를_반환한다() throws NoSuchMethodException {
        // given
        RestPointcut pointcut = new RestPointcut();
        // when
        boolean isMatch = pointcut.matches(RestTemplate.class.getMethod("responseEntityExtractor", Type.class), null);
        // then
        assertThat(isMatch).isFalse();
    }
}
