# Performance Tracker
Track your application's performance based on Spring.

[한국어 버전](./README_KR.md)

## How to use
Add dependency on your `build.gradle`

```groovy
repositories {
    maven { url 'https://jitpack.io' }
}

...

dependencies {
    testImplementation 'com.github.morak-morak:performance-tracker:master-SNAPSHOT'
}
```

And just annotate `@PerformanceTracker` on your test class. Below is an example.

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
And check your result on `~/logs/performance.log`.

If you wanna know further examples, see [this](./docs/EXAMPLE.md)

## Features
### Measurement of performance
PerformanceTracker currently supports several features with beans spring registered.  
In case of bug or inappropriate measurement, please report it on issues.

- [x] Database Access(JPA, JdbcTemplate) (using `DataSource`)
- [x] Application (using `Spring interceptor`)
- [x] API Request (using `RestTemplateBuilder`)

### [Description](./docs/Descriptions.md)
You can see the result of PerformanceTracker(which is called description) with format you specified.

- [x] logging
- [x] json

### [Context](./docs/Contexts.md)
`Context` represents the way of aggregation measured through your tests.

- [x] Method : per test method.
- [x] Endpoint : per request endpoint (e.g. URI, SQL) 

## Configuration

You can adjust properties of PerformanceTracker with spring property configurations.

```yml
com.morak.performance-tracker:
  path: {DIRECTORY_TO_WRITE_RESULT} # default is '{yourProject}/logs/'
  file: {FILE_NAME_TO_WRITE_RESULT} # default is 'performance'
  format: {DESCRIPTION_TYPE} # 'log' or 'json', default is 'log'
```

