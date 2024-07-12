package bitcamp.project3.command;

import bitcamp.project3.util.Prompt;
<<<<<<< HEAD
import bitcamp.project3.vo.*;

=======
import bitcamp.project3.vo.BookInfo;
import bitcamp.project3.vo.Grandpa;
import bitcamp.project3.vo.Guest;
import bitcamp.project3.vo.Kid;
import bitcamp.project3.vo.MemoInfo;
import bitcamp.project3.vo.NoJob;
import bitcamp.project3.vo.RentInfo;
import bitcamp.project3.vo.StoreInfo;
import bitcamp.project3.vo.Student;
>>>>>>> b06cc1e7fb9ed3f0ed5f75a1e6e361923a83eecf
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class GuestCommand implements Command {
<<<<<<< HEAD
  private final Random random = new Random();
=======
  String[] menus = {"빌려준다", "거절한다", "메모확인"};
>>>>>>> b06cc1e7fb9ed3f0ed5f75a1e6e361923a83eecf
  List<BookInfo> bookInfoList;
  List<RentInfo> rentInfoList;
  StoreInfo storeInfo;
  Map<Integer, MenuAction> menuMap = new HashMap<>();
<<<<<<< HEAD
  Guest[] guests = {new Kid(), new Student(), new NoJob(), new Grandpa()};
  String[] menus = {"빌려준다", "거절한다"};

  int randomValue = random.nextInt(4); // 0 ~ 3
  Guest guest = guests[randomValue];

  public GuestCommand(List<BookInfo> bookInfoList, List<RentInfo> rentInfoList,
      StoreInfo storeInfo) {
    menuMap.put(1, () -> accept(guest));
    menuMap.put(2, () -> reject(guest));
=======
  static String bookName;
  static Random random = new Random();
  static Guest[] guests = {
      new Kid(),
      new Student(),
      new NoJob(),
      new Grandpa()
  };
  static int guestRandomValue = random.nextInt(guests.length);
  Guest guest = guests[guestRandomValue];

  public GuestCommand(List<BookInfo> bookInfoList, List<RentInfo> rentInfoList, StoreInfo storeInfo) {
>>>>>>> b06cc1e7fb9ed3f0ed5f75a1e6e361923a83eecf
    this.bookInfoList = bookInfoList;
    this.rentInfoList = rentInfoList;
    this.storeInfo = storeInfo;
  }

  public void execute() {
    menuMap.put(1, () -> accept(guest));
    menuMap.put(2, () -> reject(guest));
    menuMap.put(3, () -> viewMemo(guest));

    int menuNo;

<<<<<<< HEAD
    /*
     *
     * 1. 빌려준다
     * 2. 안빌려준다
     * 3. 손님 정보 확인
     * */
=======
>>>>>>> b06cc1e7fb9ed3f0ed5f75a1e6e361923a83eecf
    System.out.println("---------------------------------------------------------------");
    System.out.printf("띠링띠링 [%s] 손님이 입장하셨습니다.\n", guest.getType());

<<<<<<< HEAD
    for (BookInfo bookInfo : bookInfoList) {
      System.out.print(bookInfo.getBookName() + "  ");
      System.out.print(bookInfo.getPrice() + "   ");
      System.out.print(bookInfo.getStock() + "\n");
    }

    String bookName = bookInfoList.get(randomValue).getBookName();
    System.out.printf("\n[%s] >> [%s] 책 빌릴 수 있을까요?\n", guest.getType(), bookName);
=======
    bookName = bookInfoList.get(random.nextInt(bookInfoList.size())).getBookName();
    System.out.printf("\n[%s] >> [%s] 책 빌릴 수 있을까요?\n",guest.getType(), bookName);
>>>>>>> b06cc1e7fb9ed3f0ed5f75a1e6e361923a83eecf
    System.out.println("---------------------------------------------------------------");

    printMenus();

    while (true) {
      try {
        menuNo = Prompt.inputInt(">");
        if (menuNo == 0) {
          guest = guests[random.nextInt(guests.length)];
          return;
        }
        if (getMenuTitle(menuNo) == null) {
          System.out.println("유효한 메뉴 값이 아닙니다.");
          continue;
        }
        MenuAction action = menuMap.get(menuNo);

        if (action != null) {
          action.execute();
          break;
        }

      } catch (NumberFormatException exception) {
        System.out.println("숫자만 입력해 주세요.");
      }
    }
  }

<<<<<<< HEAD
  public void accept(Guest guest) {
    System.out.println("승락");
  }

  public void reject(Guest guest) {
    System.out.println("거절");
  }



  /*
  * >> [초등학생] : [블리츠] 있나요
  1. 빌려준다 [명성 +10 , 분실 O, 피로도 +10]
  2. 안빌려준다 [명성 -20 , 분실 ㅌ, 피로도 +5]
  * */
  public void callGuest(Guest guest) {
    String bookTitle = "나루토";

    System.out.println("---------------------------------------------------------------");
    System.out.printf("[%s] >> [%s] 있나요?\n", guest.getType(), bookTitle);
    System.out.printf("손님이 [%s] 책을 찾는다...\n", bookTitle);
    System.out.println("---------------------------------------------------------------");

    System.out.println("1. 빌려준다 [명성+10, 분실 O, 피로도+10]");
    System.out.println("2. 안 빌려준다 [명성-10, 분실 X, 피로도+5]");

    while (true) {
      try {
        int menuNo = Prompt.inputInt(">");
        switch (menuNo) {
          case 1:
            LocalDate rentPeriod = LocalDate.now().plusDays(guest.getRentPeriod());

            System.out.printf("[%s]일 까지 반납하세요!\n", rentPeriod);

            RentInfo rentInfo = new RentInfo();
            rentInfo.setBookName(bookTitle);
            rentInfo.setRentStartDate(LocalDate.now());
            rentInfo.setRentEndDate(rentPeriod);
            break;
          case 2:
            System.out.println("안 빌려줄거야 명성하락, 피로도업");
            break;
          default:
            System.out.println("유효한 값이 아닙니다.");
            continue;
        }
        return;
      } catch (NumberFormatException exception) {
        System.out.println("숫자만 입력해 주세요.");
=======
  public void accept(Guest guest){
    BookInfo book = getBook();
    if (book.getStock() == 0){
      System.out.printf("현재 [%s] 책 재고가 없습니다. 다음에 찾아주세요.", book.getBookName());
      storeInfo.setReputation(storeInfo.getReputation() - guest.getReputation());
      storeInfo.setTiredness(storeInfo.getTiredness() + guest.getRentPeriod());
    }else{
      LocalDate rentPeriod = LocalDate.now().plusDays(guest.getRentPeriod());
      System.out.printf("[%s]일 까지 반납하세요!\n", rentPeriod);

      RentInfo rentInfo = new RentInfo();
      rentInfo.setGuestType(guest.getType());
      rentInfo.setBookName(book.getBookName());
      rentInfo.setRentStartDate(LocalDate.now());
      rentInfo.setRentEndDate(rentPeriod);
      rentInfoList.add(rentInfo);
      book.setStock(book.getStock() - 1);

      String memo = Prompt.input("메모 입력>");
      MemoInfo memoInfo = new MemoInfo();
      memoInfo.setMemo(memo);
      memoInfo.setWriteDate(LocalDate.now());
      guest.setMemo(memoInfo);
      System.out.println("작성 완료.");
      System.out.printf("[%s] 재고: [%d]권\n",book.getBookName(), book.getStock());

      storeInfo.setAccount(storeInfo.getAccount() + book.getPrice());

      storeInfo.setReputation(storeInfo.getReputation() + guest.getReputation());

      storeInfo.setTiredness(storeInfo.getTiredness() + guest.getRentPeriod());
    }
  }

  public void reject(Guest guest){
    System.out.println("죄송합니다. 다음에 찾아주세요.\n");

    storeInfo.setReputation(storeInfo.getReputation() - guest.getReputation());

    storeInfo.setTiredness(storeInfo.getTiredness() + guest.getRentPeriod());
  }

  public void viewMemo(Guest guest){
    if (guest.getMemos().isEmpty()){
      System.out.println("등록된 메모가 없습니다.");
    }else{
      System.out.printf("%s 손님의 메모 정보\n", guest.getType());
      for (MemoInfo memo : guest.getMemos()){
        System.out.println("작성날짜    내용");
        System.out.printf("%s \t \t%s\n",memo.getWriteDate(),memo.getMemo());
      }
      printMenus();
    }

  }


  private BookInfo getBook(){
    for (BookInfo bookInfo : bookInfoList){
      if (bookInfo.getBookName().equals(bookName)){
        return bookInfo;
>>>>>>> b06cc1e7fb9ed3f0ed5f75a1e6e361923a83eecf
      }
    }
    return null;
  }

<<<<<<< HEAD
  public void memoInfo(Guest guest) {
    System.out.printf("%s 손님의 메모 정보\n", guest.getType());
    for (MemoInfo memo : guest.getMemos()) {
      System.out.println(memo.getMemo() + memo.getWriteDate());
    }
  }
=======
>>>>>>> b06cc1e7fb9ed3f0ed5f75a1e6e361923a83eecf

  @Override
  public String[] getMenus() {
    return menus;
  }
}
