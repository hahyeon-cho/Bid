![header](https://capsule-render.vercel.app/api?type=waving&&height=200&text=경매%20서비스&fontAlign=80&fontAlignY=40&color=gradient&customColorList=23)

## 📎 관련 문서
- [📄 경매 서비스 API 문서](./docs/api-summary.md)
- <a href="https://hahyeon-cho.notion.site/52panda-32ca0fd714508067b4cee75497beb92c">
  <img src="https://upload.wikimedia.org/wikipedia/commons/4/45/Notion_app_logo.png" alt="Notion" width="18" height="18" style="vertical-align:middle;"/> 
  Notion 포트폴리오 </a>
<br>


## ◾목차
- 개요
- 서비스 구조
- 주요 API
- 기능 상세
- 트러블 슈팅 및 성능 개선
<br>


## ◾개요
중고 물품의 경매 기능을 담당하는 서비스입니다. 물품에 관한 입찰 요청을 처리하고, 물품에 대한 입찰 요청을 처리하고, 경매 마감 시간에 따라 낙찰자 선정을 포함한 경매 완료 처리를 수행합니다.

- **입찰 요청 처리**: 사용자의 물품 입찰 요청을 검증하고 저장, 즉시 낙찰 가능
- **입찰 내역 조회**: 물품에 대한 전체 입찰 정보 조회
- **경매 마감 처리**:
    - **마감 시간 관리**: 물품별 경매 마감 시간 스케줄링
    - **낙찰자 선정**: 최고가 입찰자를 낙찰자로 결정
    - **상태 변경 처리**: 물품 상태를 낙찰 완료로 갱신
    - **채팅방 생성**: 낙찰자와 판매자 간 1:1 채팅방 생성 유도
    - **알림 생성**: 낙찰 결과에 관한 알림 생성 유도
<br>


## ◾서비스 구조
경매 서비스의 입찰 처리 및 경매 마감 흐름은 다음과 같습니다.

<img src="https://github.com/user-attachments/assets/0a7d5b0f-b249-4bbe-82a3-d4120f7135aa" alt="Sequence_01" width="58%" height="58%"/> 
<br>
<br>
<br>

**※ 경매 완료 처리 (즉시 낙찰 or 종료 시간 도달 시)**  
<img src="https://github.com/user-attachments/assets/10ae26f3-b2ad-47b0-9fa7-3e51155353c0" alt="Sequence_02" width="68%" height="68%"/> 
<br>
<br>

## ◾주요 API
| 메서드 | 경로             | 설명                                |
|---------|------------------|-------------------------------------|
| POST    | /{itemId}/bid     | 경매 물품에 관한 입찰 진행          |
| GET     | /{itemId}/bid     | 경매 입찰 내역 조회                |

> 자세한 내용은 [경매 서비스 API 문서](./docs/api-summary.md)에서 확인할 수 있습니다.
<br>

## ◾기능 상세
### ◎ Function: 경매 기능
![Image](https://github.com/user-attachments/assets/be018485-36ff-457b-a944-f0e61edbf722)
- 일반 입찰 및 즉시 입찰 처리
- 입찰 데이터 저장 및 최고가 갱신
- 경매 자동 종료 처리
<br>

## ◾트러블슈팅 및 성능 개선
> 본 프로젝트 전반에 걸친 주요 문제 해결 사례와 성능 개선 내역은  
[포트폴리오 페이지 내 '주요 문제 상황 및 해결'](https://hahyeon-cho.notion.site/52panda-32ca0fd714508067b4cee75497beb92c#22fa0fd7145080fb8df9d7dd36e3181e)에서 확인하실 수 있습니다.
