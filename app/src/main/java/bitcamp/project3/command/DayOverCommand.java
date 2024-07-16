package bitcamp.project3.command;

import bitcamp.project3.util.Prompt;
import bitcamp.project3.vo.BookInfo;
import bitcamp.project3.vo.RentInfo;
import bitcamp.project3.vo.StoreInfo;

import java.util.ArrayList;
import java.util.List;

public class DayOverCommand implements Command {
  StoreInfo storeInfos;
  private List<RentInfo> rentList;
  private List<BookInfo> bookList;


  public DayOverCommand(StoreInfo storeInfos, List<RentInfo> rentList, List<BookInfo> bookList) {
    this.storeInfos = storeInfos;
    this.rentList = rentList;
    this.bookList = bookList;
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
        }
      }
    }
    System.out.println("------영업종료--------");
    //정산내역 정리하기
    Prompt.input("계속하려면 엔터를 누르세요.");
    System.out.println("------정산내역--------");
    System.out.printf("대출권수 : %d 권\n", rentGuest.size());

    System.out.printf("반납 받은 책 : %d 권\n", returnTrue.size());

    for (RentInfo rentInfo : returnTrue) {
      System.out.printf("[%s]가 [%s]를 반납했습니다.\n", rentInfo.getGuestType(), rentInfo.getBookName());
    }
    for (RentInfo rentInfo : returnFalse) {
      System.out.printf("[%s]가 [%s]를 반납 안했습니다.\n", rentInfo.getGuestType(), rentInfo.getBookName());
    }
    System.out.println("-------------------------");
    storeInfos.setDate(storeInfos.getDate().plusDays(1));
  }

  private BookInfo getBook(String bookName) {
    for (BookInfo bookInfo : bookList) {
      if (bookInfo.getBookName().equals(bookName)) {
        return bookInfo;
      }
    }
    return null;
  }
}
