<h1> bitcamp-project3 </h1>
<h2 style="margin-left: 10px;"> 도서관리 프로그램 - 만화책방 운영하기</h2>
<ul>
<li> Java기초프로그래밍 중 3번째 프로젝트</li>
<li> CRUD기능과 MENU기능을 통해 도서관리 프로그램 만들기</li>
</ul>
<h2 style="margin-left: 10px;"> 프로젝트 소개</h2>
<ul>
<li> 사용자가 만화책방을 운영하는 게임</li>
<ol style="padding-left: 10px" type="1">
<li>손님, 대여할 도서, 대여기간은 랜덤</li>
<li>손님에게 대여여부에 따라 명성도,자금,피로도 증감</li>
<li>손님 종류에 따른 도서 분실 확률이 다름</li>
</ol>
<li> CRUD기능과 MENU기능을 통해 도서관리 프로그램 만들기</li>
</ul>

<h2 style="margin-left: 10px;"> 개발환경 및 적용기술</h2>
<ul>
<li> Language & IDE</li>

![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white) ![IntelliJ IDEA](https://img.shields.io/badge/IntelliJIDEA-000000.svg?style=for-the-badge&logo=intellij-idea&logoColor=white)

<li>적용기술</li>
<ol style="padding-left: 10px" type="1">
<li> <b><u> Nested Class</u></b> : 중첩클래스를 활용하여 익명클래스, 람다식, 메서드 레퍼런스 적용</li>
<li> <b><u> Composit Pattern적용</u></b> : GoF의 Composit 패턴을 적용하여 CRUD와 UI출력 분리 (SRP)</li>
<li> <b><u> 랜덤 클래스 만들기</u></b> : ArrayList 크기 만큼의 랜덤 숫자 생성, n% 확률로 0또는1 반환</li>
</ol>
</ul>

<h2 style="margin-left: 10px;"> 목차</h2>
<ul>
<li><a href="#structure">아키텍처</a></li>
<li><a href="#functions">주요기능</a></li>
<li><a href="#outputs">결과</a></li>
<li><a href="#improvement">보완사항</a></li>

</ul>
<section id="structure">
<h2>아키텍처</h2>

![image](https://github.com/user-attachments/assets/881e91ad-10c5-4515-8015-a8713e412932)
<ul>
<li> 기본 데이터타입 : Book, RentInfo, Guest, StoreInfo</li>
    <ul style="padding-left: 10px;">
        <li>Book, RentInfo, Guest 은 App클래스에서 ArrayList로 활용</li>
        <li>StorInfo에 가게운영상황을 업데이트</li>
    </ul>
<li> Command : 손님 응대, 운영 상황, 종료/결산 명령 처리수행</li>
    <ul style="padding-left: 10px;">
        <li> GuestCommand : 랜덤 손님 호출, 대여 여부 결정, 해당 결과를 App에 선언된 ArrayList에 저장</li>
        <li> OperationCommand : App에 저장된 대출정보, 재고정보 ArrayList를 호출 하여 확인</li>
        <li> DayOverCommand : App에 저장된 대출정보 ArrayList를 통해 수입, 반납여부 확인</li>
        <li> </li>
    </ul>
<li> Menu : 각 항목별 메뉴 정보 저장 및 출력</li>
</ul>

</section>

<section id="functions">
<h2>주요기능</h2>
<h3 style="margin-left: 20px;"> 1) GuestCommand </h3>
<ul>
<details>
<summary> 코드 접기/펼치기</summary>

```java
public class GuestCommand implements Command {
  List<Guest> guests;
  List<BookInfo> bookList;
  List<RentInfo> rentInfoList;
  StoreInfo storeInfo;
  Random random = new Random();

  private int guestRandomValue;
  private Guest guest;
  private String bookName;

  public GuestCommand(
    List<BookInfo> bookInfoList, List<RentInfo> rentInfoList, 
    StoreInfo storeInfo,List<Guest> guests) 

  public String getBookName()
  public void setBookName(String bookName)
  public void setGuestRandomValue(int guestRandomValue) 
  public Guest getGuest() 
  public void setGuest(Guest guest) 
  private BookInfo getBook() 

  public void preExecute() {
    //손님랜덤설정
    //책 랜덤설정
    //대여기간 랜덤설정
  }

  public void execute(String menuName) {
    //빌려준다 메서드 실행
    //거절한다 메서드 실행
  }

  private void accept(Guest guest) {
    //확률계산을 통한 반납여부 결정
    //preExcute에서 계산된 책 호출
    //재고확인 및 반납설정
    //대여한 책 rentList에 저장
    //storeInfo 설정값(명성,자금,피로도) 변경
  }

  private void reject(Guest guest) {
    //storeInfo 설정값(명성,자금,피로도) 변경
  }
}
```
</details>
</ul>

<h3 style="margin-left: 20px;"> 2) OperationCommand </h3>
<ul>
<details>
<summary> 코드 접기/펼치기</summary>

```java
public class OperationCommand implements Command {
  private List<BookInfo> bookList;
  private List<RentInfo> rentList;
  private StoreInfo storeInfo;
  public OperationCommand(List<BookInfo> bookList, List<RentInfo> rentList, StoreInfo storeInfo) 

  public void execute(String menuName) {
    //재고조회 메서드 실행
    //서점가기 메서드 실행
    //대출현황 메서드 실행
  }

  public void checkStock() {
    //App에서 받아온 BookList 정보중 제목, 재고 출력
  }

  public void bookStore() {
    //App에서 받아온 BookList 정보 제목, 가격 출력
    //자금과 비교하여 구매여부 결정
    //bookList에 구매정보 반영
  }

  public void checkRent() {
    //App에서 받아온 rentList 출력
  }

}
```
</details>
</ul>

<h3 style="margin-left: 20px;"> 3) DayOverCommand </h3>
<ul>
<details>
<summary> 코드 접기/펼치기</summary>

```java
public class DayOverCommand implements Command {
  StoreInfo storeInfos;
  private List<RentInfo> rentList;
  private List<BookInfo> bookList;
  private List<Guest> guestList;

  public DayOverCommand(StoreInfo storeInfos, List<RentInfo> rentList, 
                        List<BookInfo> bookList,List<Guest> guestList) 

  public void execute(String menuName) {
    //결과 출력을 위해 rentGuest, returnTrue, returnFalse의 ArrayList 생성
    //rentList를 호출하여 각 결과를 rentGuest, returnTrue, returnFalse에 저장하구 출력
    //storeInfo에 영업 1일 증가, 피로도 조정
    }
}
```
</details>
</ul>

</section>

<section id="outputs">
<h2>결과</h2>
<p align="center">
  <img src="https://github.com/user-attachments/assets/2b13b748-ba9c-4e15-82f6-b8bfe3ebe6f4" alt="화면 기록 2024-07-17 오후 8 56 57" width="500">
</p>
</section>

<section id="improvement">
<h2>보완사항</h2>
<ul>
<li> composite 패턴을 적용하였는데, 완전하게 분리할 필요가 있다.(혼재된 상태) </li>
<li> command를 각 메서드에 따라서 클래스로 관리할 필요가 있다. </li>

![image](https://github.com/user-attachments/assets/f64f9620-b9c5-4890-8bfb-37f4ed8d566a)
</ul>
<!-- 결과에 대한 내용 -->
</section>

