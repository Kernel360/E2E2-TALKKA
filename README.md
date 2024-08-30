# 🚌 탈까?

## 프로젝트 소개
- [탈까?-해커톤](https://github.com/Kernel360/hackathon2-talkka)에 이은 프로젝트
- 출퇴근시간 내가 타려는 버스에 좌석는 몇개나 남아있을까?
- [공공데이터포털](https://www.data.go.kr/index.do)에서 버스 정보를 제공받아 실시간 도착정보와 남은 좌석개수 통계 및 예측을 제공하는 서비스입니다.

<br>

## 팀원 소개
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

<br>

## 기능
### 회원가입 및 로그인
<img width="270" alt="로그인1" src="https://github.com/user-attachments/assets/6227b47c-2bab-4b41-968c-4f8d4c29bb76">
<img width="270" alt="로그인3" src="https://github.com/user-attachments/assets/f21e4e97-fc41-414f-9501-d69bc32a830a">
<img width="270" alt="로그인4" src="https://github.com/user-attachments/assets/efd1ce4c-4eff-4456-99c6-77920421f247">

- OAuth2.0과 Spring Security를 이용한 네이버 소셜로그인 제공
- 세션을 이용한 로그인 구현
- 소셜인증만 된 상태와(UNREGISTERED) 회원가입이 완료된 상태(USER)의 권한을 분리하여 소셜 인증 후 추가정보 입력 로직 구현
- 마이페이지 및 닉네임 수정 기능 제공

<br>

---

<br>

### 버스 검색

<img width="270" alt="버스조회1" src="https://github.com/user-attachments/assets/97f5e876-711b-4dd8-943d-dbbd7ab1d064">
<img width="270" alt="버스조회2" src="https://github.com/user-attachments/assets/237a968f-64ff-4054-92da-d340a8a611cb">

- 비로그인 상태에서 버스-정거장 정보 조회
- 실시간 버스 도착정보 제공
  - 최대 2개 버스까지 도착정보 제공
- 선택한 버스에 대한 통계 및 예측 제공
  - 과거 같은 요일(최대 5주전까지) 시간대별 남은 좌석 통계 제공
  - 과거 통계 데이터를 바탕으로 현재 도착예정 버스의 남은좌석 예측 제공

<br>

---

<br>


### 버스 북마크
<img width="270" alt="스크린샷 2024-08-30 오전 10 36 53" src="https://github.com/user-attachments/assets/108b9357-d85d-4e30-aa05-875e494ecfad">
<img width="270" alt="스크린샷 2024-08-30 오전 10 36 07" src="https://github.com/user-attachments/assets/c20055d0-60c0-4aaf-ae75-f3270db8eda4">
<img width="270" alt="스크린샷 2024-08-30 오전 10 37 53" src="https://github.com/user-attachments/assets/8c4ad87b-a301-4189-aa8d-739d9ca67621">
<img width="270" alt="스크린샷 2024-08-30 오전 10 38 57" src="https://github.com/user-attachments/assets/0fcc644e-8dea-4354-82f1-304a34e1b662">
<img width="270" alt="스크린샷 2024-08-30 오전 10 40 02" src="https://github.com/user-attachments/assets/49b4672e-cdcd-4bd9-8ca3-5133315ba890">
<img width="270" alt="스크린샷 2024-08-30 오전 10 41 02" src="https://github.com/user-attachments/assets/1d472097-ba55-43fa-b3c1-13dc7b45766e">

- 북마크에 여러개의 버스 등록 가능
  - ex) 출근길 : 7800번, 3003번
- 실시간 버스 도착정보 제공
  - 최대 2개 버스까지 도착정보 제공
- 등록된 버스에 대한 통계 및 예측 제공
  - 과거 같은 요일(최대 5주전까지) 시간대별 남은 좌석 통계 제공
  - 과거 통계 데이터를 바탕으로 현재 도착예정 버스의 남은좌석 예측 제공

<br>

---

<br>

### 버스-정거장 리뷰
<img width="270" alt="리뷰1" src="https://github.com/user-attachments/assets/416aaf74-27d4-4928-86a3-101f43ff7773">
<img width="270" alt="리뷰2" src="https://github.com/user-attachments/assets/ae31bd68-4e82-4415-9fe4-cd248e7bf115">

- 이용한 버스-정거장에 대해 쾌적도 및 리뷰 등록 가능
- 이용한 시간대의 쾌적도를 리뷰하여 이후 예측에 활용할 예정(미구현)
- 
<br>

---

<br>

### 관리자 페이지
<kbd>
<img width="800" alt="관리자1" src="https://github.com/user-attachments/assets/5237edc4-b387-42f5-a0d4-9fb329c8d5fb" style:"border : 1px solid black;">
</kbd>

#### 관리자 대시보드
  - 사용자 관리
  - 북마크 통계
  - 리뷰 통계
  - 수집 버스노선 관리
  - 공공 api key 관리
  - 스케줄러 관리

<br>

<kbd>
   <img width="800" alt="관리자2" src="https://github.com/user-attachments/assets/e33ef513-6051-42cc-9553-6b2a348c6608" style:"border : 1px solid black;">
</kbd>

#### 사용자 관리
  - 사용자 가입정보 조회 및 회원탈퇴(삭제)

<br>

<kbd>
  <img width="800" alt="관리자3" src="https://github.com/user-attachments/assets/78a6abc5-e9bd-4153-bdd6-2b8862377247" style:"border : 1px solid black;">
</kbd>

#### 북마크 통계
  - 버스 노선-정거장 별 북마크에 등록된 횟수 오름차순 조회

<br>

<kbd>
  <img width="800" alt="관리자4" src="https://github.com/user-attachments/assets/8adc4b6e-9218-4db5-be7c-cb2cf68f8650" style:"border : 1px solid black;">
</kbd>

#### 리뷰 통계
  - 버스 노선-정거장 별 리뷰 개수 오름차순 조회

<br>

<kbd>
  <img width="800" alt="관리자5" src="https://github.com/user-attachments/assets/121f2431-3f0e-44ec-a6db-06bffa9d9fa0" style:"border : 1px solid black;">
</kbd>

#### 수집 버스노선 관리
  - 실시간 위치정보 수집 대상 노선 관리(등록/삭제)

<br>

<kbd>
  <img width="800" alt="관리자6apikey" src="https://github.com/user-attachments/assets/9fe990da-ba8e-4f9f-8124-49c8bb53ed65" style:"border : 1px solid black;">
</kbd>

#### 공공 api key 관리
  - 공공 api 사용한도가 1,000천건/1회 이기 때문에 여러개의 api키를 등록하여 사용
  - 요창 url별로 한도가 주어지기 때문에 각 url마다 사용량 따로 집계
  - 한도가 초과된 api키는 다음날까지 사용되지 않음

<br>

<kbd>
  <img width="1440" alt="관리자7" src="https://github.com/user-attachments/assets/169f3e18-800c-41f4-9828-a23f939d194d"  style:"border : 1px solid black;">
</kbd>

#### 스케줄러 관리
  - 스케줄 등록이 된 작업 관리 (커스텀 어노테이션 : @DynamicScheduled)
    - 버스 위치정보 수집 (3분간격)
    - api key 사용량 리셋 (매일 00:00)
    - 수집한 위치정보 데이터 가공 (매일 03:00)
  - 런타임 중 크론식 변경 가능
  - 한개의 작업에 여러개의 크론식 등록 가능 (구분자 "|")
<br>

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

## 외부 API
- [경기도 버스 노선 조회](https://www.data.go.kr/data/15080666/openapi.do) : 버스 노선 정보
- [경기도 정류소 조회](https://www.data.go.kr/data/15080666/openapi.do) : 버스 정거장 정보, 버스 정거장 경유 노선 정보
- [경기도 버스 위치정보 조회](https://www.data.go.kr/data/15080648/openapi.do) : 특정 노선의 현재 운행중인 버스들의 실시간 위치 정보
- [경기도 버스 도착정보 조회](https://www.data.go.kr/data/15080346/openapi.do) : 특정 정거장에 도착하는 실시간 버스 도착 정보

## 프로젝트 협업 관련 정보
[탈까? 위키](https://github.com/Kernel360/E2E2-TALKKA/wiki)
