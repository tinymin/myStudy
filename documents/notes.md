# 읽어볼만한 것
https://d2.naver.com/helloworld/4911107 (람다가 이끌어 갈 모던 Java)


# 정리할 것
- hashCode & equals
  - https://anster.tistory.com/160
  - HashList, HashMap, HashTable 등에 사용할 때는 hashCode를 정의해야한다.
  - HashList에 item을 put하게 되면 해당 객체의 hashCode를 내부에 저장하여 유일한지 판단하게 되는데, 중간에 값이 변경 된다면 hashCode로 바뀌게 된다. 하지만 이미 List에서는 값이 변경되기 이전의 hashCode를 들고 있기 때문에 hashCode로 equals를 비교하게 되면 엉뚱한 결과가 나온다. 따라서 immutable이 중요하다.
  - hashCode --> Immutable --> Builder Pattern
- HashList에서 Hash는 무엇인가
- 대용량 트래픽 대응 방법에 대한 고민
- Redis 특징 정리


# 학습 필요한 것들

- Spring을 기초부터 확실히 공부할 필요가 있음
- 디자인 패턴
- 도커
- DB도 개념이 약해서 보완 필요


# 키워드

1. 자바 GC
2. 자바 쓰레드
3. JDBC, Statement와 PreparedStatment 차이
4. 자바 스트림에 대해 깊게 이해
5. JDBC Connection Pool
6. My-SQL, MS-SQL, 오라클에서 인덱스를 는 방법 차이
7. 네트워크: 비사이드소프트에서 진행되는 모든 개발은 네트워크와의 연결 환경을 전제로 하고 있습니다. 따라서 네트워크 지식은 비사이드소프트의 개발자들에게 필수 소양입니다. 그래서 저희는 <b>웹브라우저에서 출발한 HTTP 리퀘스트 메시지가 프로토콜 스택을 호출하여 DNS 요청을 수행하고, TCP/IP를 거쳐 LAN어댑터를 통해 서버로 이동하는 과정을 공부</b>하였습니다.
