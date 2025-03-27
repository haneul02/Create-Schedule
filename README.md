## Create-Schedule

- 일정 관리 앱

## Schedule API 명세서
#### 1. 일정 저장
- HTTP 메서드 : `POST`
- URL : `/schedules`
- Request : 
```
{
  "name": "홍길동",
  "email": "hong@hong.com",
  "password": "1234",
  "content": "회의",
  "title": "일정이 없어요"
}
```
- 응답 

HTTP 상태 코드 : `400 BAD REQUEST`

실패 메시지
```
{
  "message": "모든 필드는 필수입니다."
}
```

#### 2. 전체 일정 조회
- HTTP 메서드 : `GET`
- URL : `/schedules/all?page=0&size=10`
- Response : 
```
  {
    "id": 1,
    "name": "홍길동",
    "email": "hong@hong.com",
    "content": "회의",
    "title": "일정이 없어요",
    "creation": "2025-03-27T10:00:00",
    "revision": "2025-03-27T10:00:00"
  }
```
#### 3. 단일 일정 조
- HTTP 메서드 : `GET`
- URL : `/schedules/{id}`
- Response : 
```
{
   "id": 1,
   "name": "홍길동",
   "email": "hong@hong.com",
   "content": "회의",
   "title": "일정이 없어요",
   "creation": "2025-03-27T10:00:00",
   "revision": "2025-03-27T10:00:00"
 }
```
#### 4. 일정 전체 수정(제목 + 내용 + 비밀번호 확인)
- HTTP 메서드 : `PUT`
- URL : `/schedules/{id}`
- Request : 
```
{
  "title": "수정된 팀 회의",
  "content": "변경된 회의 일정",
  "password": "1234",
  "email": "hong@hong.com"
}
```
- Response : 
```
{
  "id": 1,
  "name": "홍길동",
  "email": "hong@hong.com",
  "content": "변경된 회의 일정",
  "title": "수정된 팀 회의",
  "creation": "2025-03-27T10:00:00",
  "revision": "2025-03-27T12:00:00"
}
```
- 응답 
HTTP 상태 코드 : `400 BAD REQUEST`

실패 메시지
```
{
  "message": "제목 & 내용은 필수입니다."
}
```

HTTP 상태 코드 : `404 NOT FOUND`

실패 메시지
```
{
  "message": "수정 오류"
}
```
#### 5. 일정 내 수정(내용 + 비밀번호 확인)
- HTTP 메서드 : `PUT`
- URL : `/schedules/{id}/title`
- Request : 
```
{
  "title": "업데이트된 제목",
  "password": "1234",
  "email": "hong@example.com"
}
```
- Response : 
```
{
  "id": 1,
  "name": "홍길동",
  "email": "hong@hong.com",
  "content": "변경된 회의 일정",
  "title": "업데이트된 제목",
  "creation": "2025-03-27T10:00:00",
  "revision": "2025-03-27T12:00:00"
}
```
- 응답 
HTTP 상태 코드 : `400 BAD REQUEST`

실패 메시지
```
{
  "message": "내용은 필수입니다.."
}
```

HTTP 상태 코드 : `404 NOT FOUND`

실패 메시지
```
{
  "message": "변경된 데이터가 없습니다."
}
```

#### 6. 일정 삭제 (비밀번호 확인 필요)
- HTTP 메서드 : `DELETE`
- URL : `/schedules/{id}/delete`
- Request : 
```
{
  "password": "1234",
  "email": "hong@hong.com"
}
```
- 응답 
HTTP 상태 코드 : `404 NOT FOUND`

실패 메시지
```
{
  "message": "해당 id의 일정을 찾을 수 없습니다."
}
```
## Schedule ERD
![image](https://github.com/user-attachments/assets/1ab38f45-92d6-4cf4-b3f6-d5d18a67b948)

![image](https://github.com/user-attachments/assets/37dfb022-847c-4964-9856-56f44462b1cc)
