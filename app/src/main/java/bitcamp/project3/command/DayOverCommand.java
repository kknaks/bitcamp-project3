package bitcamp.project3.command;

import bitcamp.project3.Guest.Guest;
import bitcamp.project3.vo.BookInfo;
import bitcamp.project3.vo.RentInfo;
import bitcamp.project3.vo.StoreInfo;

import java.util.ArrayList;
import java.util.List;

public class DayOverCommand implements Command {
  StoreInfo storeInfos;
  private List<RentInfo> rentList;
  private List<BookInfo> bookList;
  private List<Guest> guestList;


  public DayOverCommand(StoreInfo storeInfos, List<RentInfo> rentList, List<BookInfo> bookList,
      List<Guest> guestList) {
    this.storeInfos = storeInfos;
    this.rentList = rentList;
    this.bookList = bookList;
    this.guestList = guestList;
  }

  @Override
  public void execute(String menuName) {
    List<RentInfo> rentGuest = new ArrayList<>();
    List<RentInfo> returnTrue = new ArrayList<>();
    List<RentInfo> returnFalse = new ArrayList<>();

    for (RentInfo rentInfo : rentList) {
      BookInfo book = getBook(rentInfo.getBookName());
      if (rentInfo.getRentStartDate().equals(storeInfos.getDate())) {
        rentGuest.add(rentInfo);
      }
      if (rentInfo.getRentEndDate().equals(storeInfos.getDate())) {
        if (rentInfo.isBookReturn()) {
          returnTrue.add(rentInfo);
          book.setStock(book.getStock() + 1);
        } else {
          returnFalse.add(rentInfo);
          book.setStock(book.getStock() - 1);
//          Guest guest = getGueset(rentInfo.getGuestType());
//          guest.setLossCount(guest.getLossCount() + 1);
        }
      }
    }
    System.out.println("--------금일 영업종료----------");
    System.out.println("\t\t [정산 내역]");
    System.out.printf("대출권수 : %d 권\n", rentGuest.size());
    System.out.printf("금일매출 : %d 원\n", rentGuest.size() * 300);
    System.out.printf("반납권수 : %d 권\n", returnTrue.size());

    for (RentInfo rentInfo : returnTrue) {
      System.out.printf("[%s]가 [%s]를 반납.\n", rentInfo.getGuestType(), rentInfo.getBookName());
    }
    for (RentInfo rentInfo : returnFalse) {
      System.out.printf("[%s]가 [%s]를 미반납.\n", rentInfo.getGuestType(), rentInfo.getBookName());
    }

    storeInfos.setDate(storeInfos.getDate().plusDays(1));
    System.out.println("-------------------------------");
  }

  private BookInfo getBook(String bookName) {
    for (BookInfo bookInfo : bookList) {
      if (bookInfo.getBookName().equals(bookName)) {
        return bookInfo;
      }
    }
    return null;
  }

  private Guest getGueset(String guests) {
    for (Guest guest : guestList) {
      if (guest.getType().equals(guests)) {
        return guest;
      }
    }
    return null;
  }
}
