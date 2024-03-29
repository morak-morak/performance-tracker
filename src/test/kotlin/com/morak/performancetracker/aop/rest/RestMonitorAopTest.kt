package com.morak.performancetracker.aop.rest

import org.assertj.core.api.AssertionsForClassTypes
import org.junit.jupiter.api.Test
import org.springframework.aop.support.AopUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.client.RestTemplateBuilder

@SpringBootTest
class RestMonitorAopTest @Autowired constructor(private val builder: RestTemplateBuilder) {
    @Test
    fun `빌더로 RestTemplate 생성시 프록시를 반환한다`() {
        // given
        val restTemplate = builder.build()
        // when
        val isProxy = AopUtils.isAopProxy(restTemplate)
        // then
        AssertionsForClassTypes.assertThat(isProxy).isTrue()
    }
}
