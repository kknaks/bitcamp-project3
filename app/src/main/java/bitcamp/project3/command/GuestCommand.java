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
  List<Guest> guests;
  String[] menus = {"빌려준다", "메모확인"};
  List<BookInfo> bookInfoList;
  List<RentInfo> rentInfoList;
  StoreInfo storeInfo;
  Map<Integer, MenuAction> menuMap = new HashMap<>();


  public GuestCommand(List<BookInfo> bookInfoList, List<RentInfo> rentInfoList,
      StoreInfo storeInfo, List<Guest> guests) {
    this.bookInfoList = bookInfoList;
    this.rentInfoList = rentInfoList;
    this.storeInfo = storeInfo;
    this.guests = guests;
  }

  public void execute() {
    int guestRandomValue = random.nextInt(guests.size());
    double checkLoss;
    final Guest guest = guests.get(guestRandomValue);

    menuMap.put(1, () -> accept(guest));
    menuMap.put(2, () -> viewMemo(guest));

    System.out.println("-------------------------");
    System.out.printf("[%s] 손님이 입장하셨습니다.\n", guest.getType());
    System.out.printf("[%s] 손님의 책 분실 횟수 : [%d] ", guest.getType(), guest.getLossCount());


    for (RentInfo rentInfo : rentInfoList){
      if (rentInfo.getGuestType().equals(guest.getType()) &&
          rentInfo.getRentEndDate().isEqual(storeInfo.getDate()) &&
          !rentInfo.isBookReturn()){
          checkLoss = generateRandomZeroOrOne(guest.getLossForce());

          BookInfo book = getBookName(rentInfo.getBookName());
          if (checkLoss == 0){
            if (book != null){
              System.out.printf("[%s] 손님이 [%s] 책을 분실했습니다.\n", guest.getType(), book.getBookName());
              book.setStock(book.getStock() - 1);
              storeInfo.setTiredness(storeInfo.getTiredness() + 2);
              guest.setLossCount(guest.getLossCount() + 1);
            }else{
              System.out.println("[%s]는 없는 책입니다");
            }
          }else{
            System.out.printf("[%s] 손님이 [%s] 책을 반납했습니다.\n", guest.getType(), book.getBookName());
            book.setStock(book.getStock() + 1);
            storeInfo.setTiredness(storeInfo.getTiredness() - 2);
          }
      }
    }

      bookName = bookInfoList.get(random.nextInt(bookInfoList.size())).getBookName();
      System.out.printf("\n[%s] >> [%s] 책 빌릴 수 있을까요?\n", guest.getType(), bookName);
      System.out.println("-------------------------");

      executeMenu();
  }

  public void accept(Guest guest) {
    BookInfo book = getBook();

    if (book.getStock() == 0) {
      System.out.println("-------------------------");
      System.out.printf("현재 [%s] 책 재고가 없습니다. 다음에 찾아주세요.\n", book.getBookName());
      System.out.println("-------------------------");
      storeInfo.setReputation(storeInfo.getReputation() - guest.getReputation());
      storeInfo.setTiredness(storeInfo.getTiredness() + guest.getRentPeriod());
    } else {
      LocalDate rentPeriod = storeInfo.getDate().plusDays(guest.getRentPeriod());
      System.out.println("-------------------------");
      System.out.printf("[%s] : [%s] 손님이 [%s] 책을 대여했습니다.\n"
          ,storeInfo.getDate(), guest.getType(),book.getBookName());
      System.out.printf("대여 기간은 [%d]일입니다. [%s]일 까지 반납하세요!\n"
          ,guest.getRentPeriod(), rentPeriod);

      RentInfo rentInfo = new RentInfo();
      rentInfo.setGuestType(guest.getType());
      rentInfo.setBookName(book.getBookName());
      rentInfo.setRentStartDate(storeInfo.getDate());
      rentInfo.setRentEndDate(rentPeriod);
      rentInfoList.add(rentInfo);
      book.setStock(book.getStock() - 1);

      System.out.printf("\n[%s] 재고: [%d]권\n", book.getBookName(), book.getStock());

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
      System.out.println("-------------------------");
      System.out.println("등록된 메모가 없습니다.");
      System.out.println("-------------------------");
      executeMenu();
    } else {
      System.out.println("-------------------------");
      System.out.printf("[%s] 메모 정보\n", guest.getType());
      System.out.println("작성날짜 \t \t \t 내용");
      for (MemoInfo memo : guest.getMemos()) {
        System.out.printf("%s \t \t %s \t \t \n", memo.getWriteDate(), memo.getMemo());
      }
      System.out.println("-------------------------");
      System.out.println("1. 빌려준다");
      System.out.println("2. 거절한다");
      while (true){
        int command = Prompt.inputInt(">");
        switch (command){
          case 1:
            accept(guest);
            break;
          case 2:
            reject(guest);
            break;
          default:
            System.out.println("유효한 메뉴 번호가 아닙니다.");
            continue;
        }
        break;
      }
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

  private BookInfo getBookName(String name) {
    for (BookInfo bookInfo : bookInfoList) {
      if (bookInfo.getBookName().equals(name)) {
        return bookInfo;
      }
    }
    return null;
  }


  private static double generateRandomZeroOrOne(double probabilityOfZero) {
    Random random = new Random();
    return random.nextDouble() < probabilityOfZero ? 0 : 1;
  }


  private void executeMenu(){
    int menuNo;

    printMenus();
    while (true) {
      try {
        menuNo = Prompt.inputInt(">");
        if (menuNo == 0) {
          return;
        }
        if (getMenuTitle(menuNo) == null) {
          System.out.println("유효한 메뉴 번호가 아닙니다.");
          continue;
        }
        MenuAction action = menuMap.get(menuNo);

        if (action != null) {
          action.execute();
        }
        break;
      } catch (NumberFormatException exception) {
        System.out.println("숫자만 입력해 주세요.");
      }

    }

  }


  @Override
  public String[] getMenus() {
    return menus;
  }
}
