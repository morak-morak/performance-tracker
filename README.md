# Performance Tracker

## TODO
- 통합 테스트
  - `@SpringBootTest` 와 `RestAssured`를 이용한 통합 테스트의 퍼포먼스를 측정한다.
  1. API 요청 응답 시간
  2. 쿼리 개수
  3. 쿼리 시간 (각 쿼리 별 평균 수행 시간)
- DB 테스트
  - `@DataJpaTest` 혹은 `@DataJdbcTest` 등을 이용한 DB 테스트의 퍼포먼스를 측정한다.
  1. 쿼리 개수
  2. 쿼리 시간 (각 쿼리 별 평균 수행 시간)


## 기능요구사항
- 사용법
  - gradle implementation을 한다.
  - annotation을 추가한다.
  - 테스트 코드를 실행한다.
  - 결과를 확인한다.
