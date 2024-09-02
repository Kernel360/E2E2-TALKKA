# 🚌 탈까?

- [탈까? - 해커톤](https://github.com/Kernel360/hackathon2-talkka)에 이은 프로젝트
- [공공 데이터포털](https://www.data.go.kr/index.do)에서 Open API를 통해 수집한 데이터로 **남은 좌석의 통계 및 예측** 데이터를 제공 하는 서비스입니다.

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

## 프로젝트 소개

### 대중들이 겪는 현실

<img width="500" alt="스크린샷 2024-09-02 오전 10 56 54" src="https://github.com/user-attachments/assets/c4a43c07-893b-4c6a-9bbb-bf2ef15844e4">

- 2022년 시행된 `광역버스 입석 금지제도`로 인해 많은 시민들이 불편함을 겪고 있습니다.

- 일반적으로 사용되는 애플리케이션에서는 **실시간 도착정보 데이터**를 제공하며 특정한 정류소로 도착하고 있는 버스의 **남은 좌석 정보**를 볼 수 있습니다.<br>하지만 입석 금지제도가 시행된 이후 해당
  정류장에 가더라도 **긴 대기 줄**로 인해 버스를 타지 못하는 상황이 발생하고 있습니다.

- 우리의 서비스는 이런 문제를 해결하기 위해 기획되었습니다.

### 어떻게 해결할 것인가?

<img width="500" alt="스크린샷 2024-09-02 오전 11 32 14" src="https://github.com/user-attachments/assets/a8b558c8-2b55-4aaa-a1e4-ebc9f0d49c99">

- 대중교통의 경우 **이동할 수 있는 경로가 다양**합니다.<br>만약 내가 경로 1의 현재 상황을 알고 있고 이동하기 어렵다 판단된다면 경로 2로 변경할 수 있습니다.

- 하지만, 실제로는 해당 정류장의 상황이 어떤지 **실시간으로 판단**하기 어렵습니다.

- 우리는 이 문제를 해결하기 위해 **과거의 데이터를 수집**하여 사용자에게 **특정 시간대의 남은 좌석 추이**를 제공하기로 결정하였습니다.
    - 사용자는 제공되는 데이터를 통해 특정 시간대에 어떤 정류장에서 어떤 버스를 타야 좀 더 쾌적하게 이동할 수 있을지를 판단할 수 있습니다.

## 데이터 가공 방식

### 자세한 정보

- [서비스 고민들 by wiki](https://github.com/Kernel360/E2E2-TALKKA/wiki/%EC%84%9C%EB%B9%84%EC%8A%A4-%EA%B3%A0%EB%AF%BC%EB%93%A4)

### 간략한 설명

- [실시간 위치정보 조회](https://www.data.go.kr/data/15080648/openapi.do)에서 제공하는 데이터를 **3분 간격**으로 수집합니다.
    - 해당 데이터는 특정 노선(ex: 3200번 버스)에 대해 전체 경유 정류장을 보여주며 각 버스들이 어느 위치에 있는지를 제공합니다.
- API 호출 횟수 제한으로 인해 우리는 **붐비는 시간대(출근 전, 퇴근 후, 야근 후)**의 데이터를 하루 동안 수집한 후 **AM 03:00에 해당 데이터를 가공**합니다.
- 데이터 가공은 1차적으로 **특정 노선에 대해 각 차량(ex: 경기12가3456)으로 분리**하며 이후 **분리 된 차량별 데이터에서 정류장별 좌석 수 저장**합니다.
    - 저장 된 데이터를 기반으로 특정 시간대에 특정 정류장을 지나는 버스 노선에 대한 좌석 수 추이, 버스 도착 시간 추이를 제공할 수 있습니다.
- 또한 [실시간 도착정보](https://www.data.go.kr/data/15080346/openapi.do)의 좌석수와 가공된 과거 데이터를 비교하여 **예상 좌석 수를 계산**할 수 있습니다.

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
<img width="600" alt="관리자1" src="https://github.com/user-attachments/assets/5237edc4-b387-42f5-a0d4-9fb329c8d5fb" style:"border : 1px solid black;">
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
   <img width="600" alt="관리자2" src="https://github.com/user-attachments/assets/e33ef513-6051-42cc-9553-6b2a348c6608" style:"border : 1px solid black;">
</kbd>

#### 사용자 관리

- 사용자 가입정보 조회 및 회원탈퇴(삭제)

<br>

<kbd>
  <img width="600" alt="admin북마크" src="https://github.com/user-attachments/assets/9bb7c79f-6e0d-4873-a7fc-7f748305d50c" style:"border : 1px solid black;">
</kbd>

#### 북마크 통계

- 버스 노선-정거장 별 북마크에 등록된 횟수 오름차순 조회

<br>

<kbd>
  <img width="600" alt="admin리뷰" src="https://github.com/user-attachments/assets/f1c50652-3606-452a-983f-e5ad4b594c80" style:"border : 1px solid black;">
</kbd>

#### 리뷰 통계

- 버스 노선-정거장 별 리뷰 개수 오름차순 조회

<br>

<kbd>
  <img width="600" alt="admin수집노선" src="https://github.com/user-attachments/assets/99678aee-ef82-4cc6-aad2-7639d188a0a4" style:"border : 1px solid black;">
</kbd>

#### 수집 버스노선 관리

- 실시간 위치정보 수집 대상 노선 관리(등록/삭제)

<br>

<kbd>
  <img width="600" alt="admin공공api키" src="https://github.com/user-attachments/assets/a905ba96-306f-47b0-8675-172c1da93592" style:"border : 1px solid black;">
</kbd>

#### 공공 api key 관리

- 공공 api 사용한도가 1,000천건/1회 이기 때문에 여러개의 api키를 등록하여 사용
- 요창 url별로 한도가 주어지기 때문에 각 url마다 사용량 따로 집계
- 한도가 초과된 api키는 다음날까지 사용되지 않음

<br>

<kbd>
  <img width="600" alt="admin스케줄러" src="https://github.com/user-attachments/assets/76cd0445-8e0e-4ee2-b0bc-1d7afce919c0" style:"border : 1px solid black;">
</kbd>

#### 스케줄러 관리

- 스케줄 등록이 된 작업 관리 (커스텀 어노테이션 : @DynamicScheduled)
    - 버스 위치정보 수집 (3분간격)
    - api key 사용량 리셋 (매일 00:00)
    - 수집한 위치정보 데이터 가공 (매일 03:00)
- 런타임 중 크론식 변경 가능
- 한개의 작업에 여러개의 크론식 등록 가능 (구분자 "|")
  <br>

## ERD

<img width="800" src="https://github.com/user-attachments/assets/ebca9fc2-3a5a-4d06-b601-bcbd14a88e63">

## 기술 스택

<img width="600" src="https://github.com/user-attachments/assets/815293f1-1052-4de8-b9f1-7b4a73d45b1c">

BE

<img src="https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white"> <img src="https://img.shields.io/badge/Spring-6DB33F?style=for-the-badge&logo=spring&logoColor=white"> <img src="https://img.shields.io/badge/Spring_Boot-6DB33F?style=for-the-badge&logo=Spring-Boot&logoColor=white"> <img src="https://img.shields.io/badge/Spring_Data_JPA-6DB33F?style=for-the-badge&logo=spring&logoColor=white"> <img src="https://img.shields.io/badge/Spring_Security-6DB33F?style=for-the-badge&logo=Spring-Security&logoColor=white"> <br>

FE

<img src="https://img.shields.io/badge/Next.js-000000?style=for-the-badge&logo=next.js&logoColor=white"> <img src="https://img.shields.io/badge/Shadcn-FF4081?style=for-the-badge&logo=shadcn&logoColor=white">

DB

<img src="https://img.shields.io/badge/MySQL-005C84?style=for-the-badge&logo=mysql&logoColor=white"><br>

Infra / System

<img src="https://img.shields.io/badge/AWS_EC2-FF9900?style=for-the-badge&logo=amazon-aws&logoColor=white"> <img src="https://img.shields.io/badge/Nginx-009639?style=for-the-badge&logo=nginx&logoColor=white"> <img src="https://img.shields.io/badge/Docker-2496ED?style=for-the-badge&logo=docker&logoColor=white"><br>

## 외부 API

- [경기도 버스 노선 조회](https://www.data.go.kr/data/15080666/openapi.do) : 버스 노선 정보
- [경기도 정류소 조회](https://www.data.go.kr/data/15080666/openapi.do) : 버스 정거장 정보, 버스 정거장 경유 노선 정보
- [경기도 버스 위치정보 조회](https://www.data.go.kr/data/15080648/openapi.do) : 특정 노선의 현재 운행중인 버스들의 실시간 위치 정보
- [경기도 버스 도착정보 조회](https://www.data.go.kr/data/15080346/openapi.do) : 특정 정거장에 도착하는 실시간 버스 도착 정보
