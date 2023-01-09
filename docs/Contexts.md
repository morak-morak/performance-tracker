# Context
`Context` represents the way of aggregation measured through your tests.
Currently there are two types for contexts, 'method', and 'endpoint'.

You can configure this type using spring property(`application.properties` or `application.yml`)

![](Scope.png)

Let's say you wrote a test code with scenario that your application gets a request, which query database once and request other API(not your application). 

## Method
In case of 'method' context type, your [description](Descriptions.md) would be like this.

```log
2023-01-09 20:35:23 INFO  com.morak.performancetracker.test.{TEST_CLASS}
2023-01-09 20:35:23 INFO      {TEST_DISPLAY_NAME}
2023-01-09 20:35:23 INFO          Result{name='{OTHER_API_ENDPOINT}', elapsed=954ms}
2023-01-09 20:35:23 INFO          Result{name='{YOUR_QUERY}', elapsed=12ms}
2023-01-09 20:35:23 INFO          Result{name='GET /{YOUR_ENDPOINT}', elapsed=1259ms}
```

## Endpoint
However, when 'Endpoint' is configured for context type, regardless of which test method executed requests or query, PerformanceTracker would measure it per endpoint.
Here is an example of 'Endpoint' context type.

```log
2023-01-09 20:32:49 INFO  REST
2023-01-09 20:32:49 INFO      Result{name='{OTHER_API_ENDPOINT}', elapsed=954ms}
2023-01-09 20:32:49 INFO  Query
2023-01-09 20:32:49 INFO      Result{name='{YOUR_QUERY}', elapsed=12ms}
2023-01-09 20:32:49 INFO  Web
2023-01-09 20:32:49 INFO      Result{name='GET /{YOUR_ENDPOINT}', elapsed=1259ms}
```

