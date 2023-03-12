# Description
Description is a result measured by PerformanceTracker.
You can see the result of PerformanceTracker(which is called description) with format you specified.
we support 2 types of description type at present.

- [x] Logging
- [x] Json

## Logging
Logging is the most simple format(so it is default option) on PerformanceTracker. It just logs on `{yourProject}/logs/performance.log` which can be modified by spring properties(see [README](../README.md)). Below is an example of description with logging format

```log
2023-01-09 20:35:23 INFO  com.morak.performancetracker.test.{TEST_CLASS}
2023-01-09 20:35:23 INFO      {TEST_DISPLAY_NAME}
2023-01-09 20:35:23 INFO          Result{name='{OTHER_API_ENDPOINT}', elapsed=954ms}
2023-01-09 20:35:23 INFO          Result{name='{YOUR_QUERY}', elapsed=12ms}
2023-01-09 20:35:23 INFO          Result{name='GET /{YOUR_ENDPOINT}', elapsed=1259ms}
```

## Json
Or, you can see it with 'json' format, which might come in handy if you want calculate statistics programmatically.

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
