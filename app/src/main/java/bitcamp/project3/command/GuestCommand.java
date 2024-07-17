package bitcamp.project3.command;

import bitcamp.project3.Guest.Guest;
import bitcamp.project3.util.CreateRandom.RandomAction;
import bitcamp.project3.vo.BookInfo;
import bitcamp.project3.vo.RentInfo;
import bitcamp.project3.vo.StoreInfo;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;

public class GuestCommand implements Command {


  List<Guest> guests;
  List<BookInfo> bookList;
  List<RentInfo> rentInfoList;
  StoreInfo storeInfo;
  Random random = new Random();

  private int guestRandomValue;
  private Guest guest;
  private String bookName;

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
    RandomAction r1 = random::nextInt;
    setGuestRandomValue(r1.randomDice(guests.size()));
    setGuest(guests.get(guestRandomValue));

    //책 랜덤설정
    setBookName(bookList.get(r1.randomDice(bookList.size())).getBookName());

    //대여기간 랜덤설정
    guest.setRentPeriod(r1.randomDice(7) + 1);
  }

  public void execute(String menuName) {
    System.out.printf("[%s]%n", menuName);

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
    RandomAction r2 = n -> {
      double probability = ((double) n) / 100;
      return random.nextDouble() < probability ? 0 : 1;
    };

    if (book.getStock() <= 0) {
      System.out.println("-------------------------");
      System.out.printf("[주인놈] >> 현재 [%s] 책 재고가 없습니다. 다음에 찾아주세요.%n", book.getBookName());
      System.out.println("-------------------------");
      storeInfo.setReputation(storeInfo.getReputation() - guest.getReputation());
      storeInfo.setTiredness(storeInfo.getTiredness() + guest.getRentPeriod());
    } else {
      LocalDate rentPeriod = storeInfo.getDate().plusDays(guest.getRentPeriod());
      boolean bookLoss = r2.randomDice(guest.getLossForce()) == 1;

      System.out.printf("[주인놈] >> 오늘이 [%s]이니까...%n", storeInfo.getDate());
      System.out.printf("[주인놈] >> [%s]일..후면..[%s]일 까지 반납하세요!%n%n", guest.getRentPeriod(),
          rentPeriod);

      RentInfo rentInfo = new RentInfo();
      rentInfo.setGuestType(guest.getType());
      rentInfo.setBookName(book.getBookName());
      rentInfo.setRentStartDate(storeInfo.getDate());
      rentInfo.setRentEndDate(rentPeriod);
      rentInfo.setBookReturn(bookLoss);
      rentInfoList.add(rentInfo);

      System.out.println("------- 대여 정보 -------");
      System.out.printf("대여자: %s%n", guest.getType());
      System.out.printf("대여 도서: %s%n", book.getBookName());
      System.out.printf("대여 일자: %s%n", storeInfo.getDate());
      System.out.printf("반납 일자: %s%n", rentPeriod);
      book.setStock(book.getStock() - 1);
      System.out.println("-------------------------");
      System.out.printf("도서 재고: %d권 남았습니다.%n%n", book.getStock());



      storeInfo.setAccount(storeInfo.getAccount() + (guest.getRentPeriod() * 300));
      storeInfo.setReputation(storeInfo.getReputation() + guest.getReputation());
      storeInfo.setTiredness(storeInfo.getTiredness() + guest.getTiredness());
    }
  }

  private void reject(Guest guest) {
    System.out.println("[주인놈] >> 죄송합니다. 찾으시는 책은 없습니다.%n");

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

}
