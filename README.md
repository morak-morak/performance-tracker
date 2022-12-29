# Performance Tracker
Track your application's performance based on Spring.

## How to use
Just annotate `@PerformanceTracker` on your test class. Below is an example.

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
And check your result on `~/logs/performance.log`
