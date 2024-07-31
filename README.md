# 탈까?

## 기획
- [탈까?-해커톤](https://github.com/Kernel360/hackathon2-talkka)에 이은 프로젝트
- 대중교통 이용에 있어 고민을 나누고 나의 경로에 대해 혼잡도 정보를 제공합니다.

## 기능
- 대중교통 이동 경로에 대한 혼잡도 정보 제공
  - 출발지 - 목적지에 따른 경로 제공
  - 경로에서의 광역버스, 지하철의 혼잡도 통계 제공
  - 실시간 버스, 지하철 도착 상황 제공
- 교통 수단에 대한 리뷰
  - 광역버스, 지하철의 탑승지 / 시간에 따른 쾌적도 리뷰
- 나의 경로에 대한 관련 질문 답변
  - "출근시간대에 수원역에서 잠실 가려면 어떻게 가는게 제일 괜찮은가요?"
  - 교통 관련 질문 답변

## 기술 스택
### BE
- Spring Boot 3.3.2 / Java 17
- Spring Security (with Session & Cookie)
- Spring Data JPA
- MySQL 8

### FE
- Next.js
- Shadcn

### Infra / System
- AWS EC2
- AWS RDS
- Docker / Docker compose

## 프로젝트 협업 관련 정보
[탈까? 위키](https://github.com/Kernel360/E2E2-TALKKA/wiki)
