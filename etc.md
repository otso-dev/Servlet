### 구조

### Client

URL : /role
METHOD: GET, POST  
GET요청 : /role?key=value
POST요쳥 : {key:value}

### WAS

Servlet은 tomcat이라는 WAS를 이용
IP는 PORT이다.

### filter

- 전처리와 후처리로 나누어짐
- 여러겹으로 존재할수도 있음
- 우선순위도 정할수 있음
- 전처리:

### Servlet

doGet: Client에서 보내준 GET요청을 처리하는 곳  
doPost : Client에서 보내준 POST요청을 처리하는 곳

### Service

비즈니스 로직을 구현하고 처리하는 곳(70% ~ 80%)

### Repository

Service에서 구현하지 않은 것을 Repository 또는 다른 곳에서 구현

### DB

저장된 데이터

### 데이터 전송과정

**WAS와 Server 중간에 filter가 있으면 filter를 거치고 Server로 가고 Client로 간다.**

- Client가 Server에 요청했을때  
  **Client -> WAS -> Servlet -> Service -> Repository -> DB**
- Server가 요청받은 데이터를 가지고 응답했을 때  
  **Client <- WAS <- Servlet <- Service <- Repository <- DB**

### Client 저장소

- LocalStorage
- Cookies : Client가 받은 Session(JSID)을 저장하는 곳

### Server 저장소

- Session : 브라우저를 닫으면 Session에 있는 key값이 날라간다. 그래서 다시 열면 로그인이 필요하다. 다시 로그인을 하게되면 Session에 새로운 ID값을 부여한다. Session은 유지시간이 존재한다.(default: 30분)  
  브라우저를 닫지 않는 한 Session이 유지가 된다.  
  Client가 요청을 보내면 무조건 Session에 저장소를 각각의 Client가 가지게 됨.
- context : 전역 저장소, 객체를 저장할 때 쓰임 (모든걸 저장하는게 가능) 설정 값이 존재함
- Request : 해당 요청에서만 저장되는 곳.

  위 3가지 전부 set get Attribute로 쓴다.

  #### Attribute

  Attribute는 key와 value가 있다. Attribute에 Session에 저장할 key값을 저장한다.

### JWT

Session과는 반대로 서버 저장소에 key값을 저장하지 않고, Client의 JWT라는 Token을 준다. 이 Token을 Client는 LocalStorage나 Cookies의 저장을 한다. 이 Token은 암호화가 되어있다.  
JWT에 시크릿 key가 있는데 이 key로 암호화된 Token을 복호화를 하여 정상적인 인증인지 아닌지 판단한다.

Token에는 리프레쉬 Token과 에쎄스 Token이 있다.
리프레쉬 Token은 로그인할 때 사용하고 에쎄스 Token은 인증한 시간안에
접근이 가능한 것을 사용할 때 쓰인다.
