package bitcamp.project3.command;

import bitcamp.project3.util.CreateRandom.RandomAction;
import bitcamp.project3.util.CreateRandom.RandomNum;
import bitcamp.project3.util.CreateRandom.RandomZero;
import bitcamp.project3.vo.BookInfo;
import bitcamp.project3.vo.Guest;
import bitcamp.project3.vo.RentInfo;
import bitcamp.project3.vo.StoreInfo;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GuestCommand implements Command {


  List<Guest> guests;
  //String[] menus = {"빌려준다", "거절한다"};
  List<BookInfo> bookList;
  List<RentInfo> rentInfoList;
  StoreInfo storeInfo;
  Map<Integer, MenuAction> menuMap = new HashMap<>();
  RandomAction randomNum = new RandomNum();
  RandomAction randomZero = new RandomZero();

  private int guestRandomValue;
  private Guest guest;
  private String bookName;
  private int period;

  public GuestCommand(List<BookInfo> bookInfoList, List<RentInfo> rentInfoList, StoreInfo storeInfo,
      List<Guest> guests) {
    this.bookList = bookInfoList;
    this.rentInfoList = rentInfoList;
    this.storeInfo = storeInfo;
    this.guests = guests;
  }

  public String getBookName() {
    return bookName;
  }

  public void setBookName(String bookName) {
    this.bookName = bookName;
  }

  public int getGuestRandomValue() {
    return guestRandomValue;
  }

  public void setGuestRandomValue(int guestRandomValue) {
    this.guestRandomValue = guestRandomValue;
  }

  public Guest getGuest() {
    return guest;
  }

  public void setGuest(Guest guest) {
    this.guest = guest;
  }

  public void preExecute() {
    //손님랜덤설정
    setGuestRandomValue(randomNum.randomDice(guests.size()));
    setGuest(guest = guests.get(guestRandomValue));

    //책 랜덤설정
    setBookName(bookList.get(randomNum.randomDice(bookList.size())).getBookName());

    //대여기간 랜덤설정
    guest.setRentPeriod(randomNum.randomDice());
  }

  public void execute(String menuName) {
    System.out.printf("[%s]\n", menuName);

    //    double checkLoss;
    //    for (RentInfo rentInfo : rentInfoList) {
    //      if (rentInfo.getGuestType().equals(guest.getType()) && rentInfo.getRentEndDate()
    //          .isEqual(storeInfo.getDate()) && !rentInfo.isBookReturn()) {
    //
    //        checkLoss = randomZero.randomDice(guest.getLossForce());
    //
    //        BookInfo book = getBookName(rentInfo.getBookName());
    //        if (checkLoss == 0) {
    //          if (book != null) {
    //            System.out.printf("[%s] 손님이 [%s] 책을 분실했습니다.\n", guest.getType(), book.getBookName());
    //            book.setStock(book.getStock() - 1);
    //            storeInfo.setTiredness(storeInfo.getTiredness() + guest.getTiredness());
    //            guest.setLossCount(guest.getLossCount() + 1);
    //          } else {
    //            System.out.println("[%s]는 없는 책입니다");
    //          }
    //        } else {
    //          System.out.printf("[%s] 손님이 [%s] 책을 반납했습니다.\n", guest.getType(), book.getBookName());
    //          book.setStock(book.getStock() + 1);
    //          rentInfo.setBookReturn(true);
    //
    //          storeInfo.setTiredness(storeInfo.getTiredness() - guest.getTiredness());
    //        }
    //      } else {
    //        System.out.println("---------------------------------------log");
    //      }
    //    }

    switch (menuName) {
      case "빌려준다":
        this.accept(guest);
        break;
      case "거절한다":
        this.reject(guest);
        break;
    }
  }

  private void accept(Guest guest) {
    BookInfo book = getBook();

    if (book.getStock() == 0) {
      System.out.println("-------------------------");
      System.out.printf("[주인놈] >> 현재 [%s] 책 재고가 없습니다. 다음에 찾아주세요.\n", book.getBookName());
      System.out.println("-------------------------");
      storeInfo.setReputation(storeInfo.getReputation() - guest.getReputation());
      storeInfo.setTiredness(storeInfo.getTiredness() + guest.getRentPeriod());
    } else {
      LocalDate rentPeriod = storeInfo.getDate().plusDays(guest.getRentPeriod());
      boolean bookLoss = randomZero.randomDice(guest.getLossForce()) == 1;

      System.out.printf("[주인놈] >> 오늘이 [%s]이니까...[%s]일..후면..[%s]일 까지 반납하세요!\n\n",
          storeInfo.getDate(), guest.getRentPeriod(), rentPeriod);

      System.out.println("------- 대여 정보 -------");
      System.out.printf("날짜: %s%n", storeInfo.getDate());
      System.out.printf("대여자: %s%n", guest.getType());
      System.out.printf("대여 도서: %s%n", book.getBookName());
      System.out.printf("도서 재고: %d권 남았습니다.%n%n", book.getStock() - 1);
      book.setStock(book.getStock() - 1);

      RentInfo rentInfo = new RentInfo();
      rentInfo.setGuestType(guest.getType());
      rentInfo.setBookName(book.getBookName());
      rentInfo.setRentStartDate(storeInfo.getDate());
      rentInfo.setRentEndDate(rentPeriod);
      rentInfo.setBookReturn(bookLoss);
      rentInfoList.add(rentInfo);

      System.out.printf("\n[주인놈] >> 오늘이 [%s]이니까...[%s]일..후면..[%s]일 까지 반납하세요!\n",
          storeInfo.getDate(), guest.getRentPeriod(), rentPeriod);

      storeInfo.setAccount(storeInfo.getAccount() + (guest.getRentPeriod() * 2));
      storeInfo.setReputation(storeInfo.getReputation() + guest.getReputation());
      storeInfo.setTiredness(storeInfo.getTiredness() + guest.getTiredness());
    }
  }

  private void reject(Guest guest) {
    System.out.println("[주인놈] >> 죄송합니다. 찾으시는 책은 없습니다.\n");

    storeInfo.setReputation(storeInfo.getReputation() - guest.getReputation());
    storeInfo.setTiredness(storeInfo.getTiredness() + guest.getTiredness());
  }

  private BookInfo getBook() {
    for (BookInfo bookInfo : bookList) {
      if (bookInfo.getBookName().equals(bookName)) {
        return bookInfo;
      }
    }
    return null;
  }

  private BookInfo getBookName(String name) {
    for (BookInfo bookInfo : bookList) {
      if (bookInfo.getBookName().equals(name)) {
        return bookInfo;
      }
    }
    return null;
  }

}
