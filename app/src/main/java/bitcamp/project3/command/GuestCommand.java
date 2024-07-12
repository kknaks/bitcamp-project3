package bitcamp.project3.command;

import bitcamp.project3.util.Prompt;
import bitcamp.project3.vo.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class GuestCommand implements Command {

  static String bookName;
  static Random random = new Random();
  static Guest[] guests = {new Kid(), new Student(), new NoJob(), new Grandpa()};
  static int guestRandomValue = random.nextInt(guests.length);
  String[] menus = {"빌려준다", "거절한다", "메모확인"};
  List<BookInfo> bookInfoList;
  List<RentInfo> rentInfoList;
  StoreInfo storeInfo;
  Map<Integer, MenuAction> menuMap = new HashMap<>();
  Guest guest = guests[guestRandomValue];

  public GuestCommand(List<BookInfo> bookInfoList, List<RentInfo> rentInfoList,
      StoreInfo storeInfo) {
    this.bookInfoList = bookInfoList;
    this.rentInfoList = rentInfoList;
    this.storeInfo = storeInfo;
  }

  public void execute() {
    menuMap.put(1, () -> accept(guest));
    menuMap.put(2, () -> reject(guest));
    menuMap.put(3, () -> viewMemo(guest));

    int menuNo;

    System.out.println("---------------------------------------------------------------");
    System.out.printf("띠링띠링 [%s] 손님이 입장하셨습니다.\n", guest.getType());

    bookName = bookInfoList.get(random.nextInt(bookInfoList.size())).getBookName();
    System.out.printf("\n[%s] >> [%s] 책 빌릴 수 있을까요?\n", guest.getType(), bookName);
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

  public void accept(Guest guest) {
    BookInfo book = getBook();
    if (book.getStock() == 0) {
      System.out.printf("현재 [%s] 책 재고가 없습니다. 다음에 찾아주세요.", book.getBookName());
      storeInfo.setReputation(storeInfo.getReputation() - guest.getReputation());
      storeInfo.setTiredness(storeInfo.getTiredness() + guest.getRentPeriod());
    } else {
      LocalDate rentPeriod = storeInfo.getDate().plusDays(guest.getRentPeriod());
      System.out.printf("[%s]일 까지 반납하세요!\n", rentPeriod);

      RentInfo rentInfo = new RentInfo();
      rentInfo.setGuestType(guest.getType());
      rentInfo.setBookName(book.getBookName());
      rentInfo.setRentStartDate(storeInfo.getDate());
      rentInfo.setRentEndDate(rentPeriod);
      rentInfoList.add(rentInfo);
      book.setStock(book.getStock() - 1);

      String memo = Prompt.input("메모 입력>");
      MemoInfo memoInfo = new MemoInfo();
      memoInfo.setMemo(memo);
      memoInfo.setWriteDate(storeInfo.getDate());
      guest.setMemo(memoInfo);
      System.out.println("작성 완료.");
      System.out.printf("[%s] 재고: [%d]권\n", book.getBookName(), book.getStock());

      storeInfo.setAccount(storeInfo.getAccount() + book.getPrice());

      storeInfo.setReputation(storeInfo.getReputation() + guest.getReputation());

      storeInfo.setTiredness(storeInfo.getTiredness() + guest.getRentPeriod());
    }
  }

  public void reject(Guest guest) {
    System.out.println("죄송합니다. 다음에 찾아주세요.\n");

    storeInfo.setReputation(storeInfo.getReputation() - guest.getReputation());

    storeInfo.setTiredness(storeInfo.getTiredness() + guest.getRentPeriod());
  }

  public void viewMemo(Guest guest) {
    if (guest.getMemos().isEmpty()) {
      System.out.println("등록된 메모가 없습니다.");
    } else {
      System.out.printf("%s 손님의 메모 정보\n", guest.getType());
      for (MemoInfo memo : guest.getMemos()) {
        System.out.println("작성날짜    내용");
        System.out.printf("%s \t \t%s\n", memo.getWriteDate(), memo.getMemo());
      }
      printMenus();
    }

  }


  private BookInfo getBook() {
    for (BookInfo bookInfo : bookInfoList) {
      if (bookInfo.getBookName().equals(bookName)) {
        return bookInfo;
      }
    }
    return null;
  }

  @Override
  public String[] getMenus() {
    return menus;
  }
}
