# Performance Tracker
스프링 기반 어플리케이션의 성능을 측정합니다.

##  사용법
`build.gradle` 에 아래 의존성을 추가해주세요.

```groovy
repositories {
    maven { url 'https://jitpack.io' }
}

...

dependencies {
    testImplementation 'com.github.morak-morak:performance-tracker:master-SNAPSHOT'
}
```

테스트하려는 클래스에 `@PerformanceTracker` 어노테이션을 붙여주세요. 아래는 예시입니다.

```java
@PerformanceTracker
@JdbcTest
class MyTest {
    
    private final JdbcTemplate jdbcTemplate;
    
    @Autowired
    public MyTest(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    
    public void test1() {
        jdbcTemplate.execute("...");
    }
}
```

프로젝트의 `~/logs/performance.log` 디렉토리에서 결과를 확인할 수 있습니다.


## 기능
### 성능 측정
PerformanceTracker는 스프링에 등록된 빈을 이용해 여러 기능을 제공합니다.  
적절하지 않은 성능 측정이나 버그를 발견할 경우 이슈를 남겨주세요. 

- [x] 데이터베이스 접근(JPA, JdbcTemplate) (using DataSource)
- [x] 어플리케이션 (using Spring interceptor)
- [x] API 요청 (using RestTemplateBuilder)

### Description
PerformanceTracker의 결과물(description)을 지정한 format으로 확인할 수 있습니다.

- [x] logging
- [x] json

## 설정

스프링 property 설정을 기반으로 PerformanceTracker의 속성을 설정할 수 있습니다.

```yml
com.morak.performance-tracker:
  path: {DIRECTORY_TO_WRITE_RESULT} # default is '{yourProject}/logs/'
  file: {FILE_NAME_TO_WRITE_RESULT} # default is 'performance'
```

