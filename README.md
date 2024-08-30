# 🚌 탈까?

## 프로젝트 소개
- [탈까?-해커톤](https://github.com/Kernel360/hackathon2-talkka)에 이은 프로젝트
- 출퇴근시간 내가 타려는 버스에 좌석는 몇개나 남아있을까?
- [공공데이터포털](https://www.data.go.kr/index.do)에서 버스 정보를 제공받아 실시간 도착정보와 남은 좌석개수 통계 및 예측을 제공하는 서비스입니다.

## 팀원
<table>
  <tbody>
    <tr>
      <td align="center">
        <a href="https://github.com/JuneParkCode">
          <img src="https://avatars.githubusercontent.com/u/81505228?v=4" width="100px;" alt=""/><br />
          <sub>
            <b>팀장 : 박성준</b>
          </sub>
        </a><br />
      </td>
      <td align="center">
        <a href="https://github.com/Gyaak">
          <img src="https://avatars.githubusercontent.com/u/145682992?v=4" width="100px;" alt=""/><br />
          <sub><b>팀원 : 박수형</b></sub>
        </a><br />
      </td>
      <td align="center">
        <a href="https://github.com/ss0ngcode">
          <img src="https://avatars.githubusercontent.com/u/86779839?v=4" width="100px;" alt=""/><br />
          <sub>
            <b>팀원 : 송해덕</b>
          </sub>
        </a><br />
      </td>
  </tbody>
</table>

팀장: 박성준
팀원: 박수형, 송해덕

## 기능
- 실시간 버스 도착정보 제공
  - 최대 2개 버스까지 도착정보 제공
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

### FE -> 수정 필요
- Next.js
- Shadcn

### Infra / System -> 수정 필요
- AWS EC2
- AWS RDS
- Docker / Docker compose

## 프로젝트 협업 관련 정보
[탈까? 위키](https://github.com/Kernel360/E2E2-TALKKA/wiki)
