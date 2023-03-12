
# PerformanceTracker Example

## Database Access Example

- Supported Spring Beans
  - `PreparedStatement`
  - ...
  
```java
@JdbcTest
@PerformanceTracker
public class JdbcTemplateTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    void test() {
        // given
        String name = "performance-tracker";
        jdbcTemplate.update("insert into team (name) values (?)", name);

        // when
        Team team = jdbcTemplate.queryForObject(
                "select * from team where name = ?",
                (rs, rowNum) -> new Team(rs.getLong("id"), rs.getString("name")),
                name);

        // when
        assertThat(team.getName()).isEqualTo(name);
    }
}
```

```log
2023-01-09 16:57:45 INFO  │ Query
2023-01-09 16:57:45 INFO  ├── Result{name='select * from team where name = ?', elapsed=9ms}
2023-01-09 16:57:45 INFO  ├── Result{name='insert into team (name) values (?)', elapsed=3ms}
```

## Rest API Example

- supported spring beans
  - `RestTemplateBuilder`
  - ...
  
```java
@WebMvcTest
@PerformanceTracker
@AutoConfigureWebClient
public class RestTemplateTest {

    @Autowired
    private RestTemplateBuilder builder;

    @Test
    void test() {
        URI uri = URI.create("http://your-domain.com");
        RestTemplate restTemplate = builder.build();
        YourResponseType response = restTemplate.getForEntity(uri, YourResponseType.class);
        assertThat(response).isNotNull();
    }
}
```

```log
2023-01-09 17:33:19 INFO │ REST
2023-01-09 17:33:19 INFO ├── Result{name='http://your-domain.com', elapsed=938ms}
```

## Application 
- Supported by [Spring interceptor](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/HandlerInterceptor.html)

```java
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@PerformanceTracker
class ApplicationTest {

    @LocalServerPort
    int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }
    
    @Test
    void sample() {
        MyResponse expected = MyResponse("hello");
        ExtractableResponse<Response> response = RestAssured.given().when().get("/sample").then().extract();
        MyResponse actual = response.body().as(MyResponse.class);
        assertThat(response).isEqualTo(actual);
    }
}
```

```log
2023-01-09 20:32:49 INFO │ Web
2023-01-09 20:32:49 INFO ├── Result{name='GET /sample', elapsed=1259ms}
```
