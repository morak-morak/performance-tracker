# Description
PerformanceTracker 에 의해 측정된 결과물(Description이라고 부릅니다.)을 지정한 포맷 형태로 볼 수 있습니다. 현재 두 가지의 포맷을 지원합니다. 

- [x] Logging
- [x] Json

## Logging
로깅은 PerformanceTracker에서 가장 간단한(그래서 기본으로 설정되어있는) 포맷입니다. `{yourProject}/logs/performance.log` 에 로그를 남깁니다. (해당 설정은 스프링 프로퍼티로 설정 가능합니다.) 아래는 로깅 포맷의 예시입니다. 

```log
2023-01-09 20:35:23 INFO  com.morak.performancetracker.test.{TEST_CLASS}
2023-01-09 20:35:23 INFO      {TEST_DISPLAY_NAME}
2023-01-09 20:35:23 INFO          Result{name='{OTHER_API_ENDPOINT}', elapsed=954ms}
2023-01-09 20:35:23 INFO          Result{name='{YOUR_QUERY}', elapsed=12ms}
2023-01-09 20:35:23 INFO          Result{name='GET /{YOUR_ENDPOINT}', elapsed=1259ms}
```

## Json
또는, Json 포맷으로 볼 수 있습니다. 통계를 계산하고자 할 때 특히 유용할 수 있습니다.

```json
[
  {
    "contexts": [
      {
        "name": "com.morak.performancetracker.test.{TEST_CLASS}",
        "scopes": [
          {
            "name": "{TEST_DISPLAY_NAME}",
            "summaries": [
              {
                "name": "{YOUR_QUERY}",
                "elapsed": 954
              },
              {
                "name": "GET /rest",
                "elapsed": 12
              },
              {
                "name": "GET /{YOUR_ENDPOINT}",
                "elapsed": 1259
              },
            ]
          },
          {
            "name": "{OTHER_TEST_DISPLAY_NAME}",
            "summaries": [
              ...
            ]
          }
        ]
      }
    ]
  }
]
```
