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
    System.out.println("------영업종료--------");
    System.out.println("------정산내역--------");
    //정산내역 정리하기
    List<RentInfo> rentGuest = new ArrayList<>();
    List<RentInfo> returnTrue = new ArrayList<>();
    List<RentInfo> returnFalse = new ArrayList<>();

    for (RentInfo rentInfo : rentList) {
      if (rentInfo.getRentStartDate().equals(storeInfos.getDate())) {
        rentGuest.add(rentInfo);
      }
      if (rentInfo.getRentEndDate().equals(storeInfos.getDate())) {
        if (rentInfo.isBookReturn()) {
          returnTrue.add(rentInfo);
        } else {
          returnFalse.add(rentInfo);
        }
      }
    }
    Prompt.input("계속하려면 엔터를 누르세요.");
    System.out.printf("대출권수 : %d", rentGuest.size());
    System.out.printf("반납 받은 책 : %d", returnTrue.size());
    // 명성 몇 +? 피로도 몇 +? 자금 몇 +?

    //분실한 책 출력
    //대출 목록에서 endDate도래 한것들을 선택 반납여부 확인
    //반납하면 +1, 분실하면 -1
    //도래된것은 rentlist에서 제거
    //북리스트에서 분실한 책 수 만큼 빼기
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
