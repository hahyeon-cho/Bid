# 경매 서비스 APi 목록
52panda 플랫폼의 경매 서비스에서 제공하는 API 목록입니다.
- Base URL: `/api/v1`
- 공통 응답 구조: `{ success, data, error }`  
※ 아래 예시에서는 data 필드의 내부 값만 표시합니다.

---

### 입찰 요청
POST /auth/auction/item/{itemId}/bid
```Json
req body
{
  "bidPrice": 10000
}
```

### 입찰 내역 조회
GET /no-auth/auction/item/{itemId}/bid
```Json
res body
{
  "infos": [
    { "bidderNickname": "입찰자A", "bidPrice": 12000, "bidAt": "2024-06-11T10:15:30" },
    { "bidderNickname": "입찰자B", "bidPrice": 11000, "bidAt": "2024-06-11T09:45:10" },
    ...
  ],
  "price": {
    "currentPrice": 12000,
    "bidCount": 5
  }
}
```
