## API 개발 기본
* API 관련 소스는 패키지를 분리한다.
* API 요청 파라미터를 엔티티로 받으면 안되는 이유
  - 엔티티 스펙 변경시 API에도 영향이 가기 때문
  - 또한 반환 객체에 엔티티를 사용하면 중요 정보 (예: 비밀번호, 개인정보 등) 이 포함될 수 있다.
* 각 API 전용 request 및 response 객체 (DTO : Data Transfer Object)를 만든다.
* 특정로직 수행 후 결과 키 값을 반환하기 보다는 반환객체에 넘길 값은 컨트롤러에서 요청으로 넘어온 키 값으로 따로 조회후 조회결과 엔티티에서 ResponseDto에 맞게 값을 채워 전송한다.
* @NotEmpty @Valid @JsonIgnore
* ResponseDto 를 배열 그대로 넘길경우 확장성이 매우 떨어진다.
