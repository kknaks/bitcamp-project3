package bitcamp.project3.command;

import bitcamp.project3.util.Prompt;
import bitcamp.project3.vo.BookInfo;
import bitcamp.project3.vo.RentInfo;
import bitcamp.project3.vo.StoreInfo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StockCommand implements Command {

  String[] menus = {"재고조회", "서점가기", "대출현황"};
  Map<Integer, MenuAction> menuMap = new HashMap<>();
  private List<BookInfo> bookList;
  private List<RentInfo> rentList;
  private StoreInfo storeInfo;

  public StockCommand(List<BookInfo> bookList, List<RentInfo> rentList, StoreInfo storeInfo) {
    this.bookList = bookList;
    this.rentList = rentList;
    this.storeInfo = storeInfo;
  }

  //  public static void main(String[] args) {
  //    //    String s1 = "가가가";
  //    //    String s2 = "가가가가";
  //    //    String s3 = "가가가가가";
  //
  //    // 각 문자열의 실제 출력 길이를 계산
  //    int width = 15; // 원하는 폭
  //    String s4 = "";
  //    for (int i = 0; i < 10; i++){
  //      s4 += "가";
  //      System.out.printf("%-" + (getAdjustedWidth(s4, width))+ "s|\n", s4);
  //      System.out.printf("%d \n",getAdjustedWidth(s4, width));
  //      System.out.printf("----------\n");
  //    }
  //  }
  //  private static int getAdjustedWidth(String s, int width) {
  //    int length = s.length();
  //    int nonAsciiCount = 0;
  //
  //    for (char c : s.toCharArray()) {
  //      if (c > 127) {
  //        nonAsciiCount++;
  //      }
  //    }
  //
  //    return width - ((2 * nonAsciiCount - 1) / 2);
  //  }

  public void execute() {
    menuMap.put(1, this::checkStock);
    menuMap.put(2, this::checkRent);
    menuMap.put(3, this::bookStore);

    while (true) {
      printMenus();
      try {
        int menuNo = Prompt.inputInt("메인>");
        if (menuNo == 0) {
          return;
        }
        if (getMenuTitle(menuNo) == null) {
          System.out.println("유효한 메뉴 값이 아닙니다.");
          continue;
        }
        menuMap.get(menuNo).execute();
      } catch (NumberFormatException exception) {
        System.out.println("숫자만 입력해 주세요.");
      }
    }
  }

  public void checkStock() {
    int strWidth = 10;
    System.out.println("재고현황");
    System.out.println("-----------------------------");
    System.out.printf("%-10s|%10s\n", "책제목", "보유권수");
    System.out.println("-----------------------------");
    for (BookInfo bookInfo : bookList) {
      System.out.printf("%-10s|", bookInfo.getBookName());
      System.out.printf("%10s권\n", bookInfo.getStock());
    }
    System.out.println("-----------------------------");
  }

  public void bookStore() {
    System.out.println("서점");
    for (int i = 0; i < bookList.size(); i++) {
      BookInfo bookInfo = bookList.get(i);
      System.out.println(bookInfo.toString());
    }
    try {
      int menuNo = Prompt.inputInt("구매할 책>");
      if (menuNo > bookList.size() || menuNo < 0) {
        System.out.println("없는책");
      } else {
        BookInfo book = bookList.get(menuNo - 1);
        int change = storeInfo.getAccount() - book.getPrice();
        if (change < 0) {
          System.out.println("돈없음");
          return;
        }
        storeInfo.setAccount(change);
        bookList.get(menuNo - 1).setStock(book.getStock() + 1);
        System.out.printf("%s 구매완료\n", book.getBookName());
      }
    } catch (NumberFormatException exception) {
      System.out.println("숫자만 입력해 주세요.");
    }
  }

  public void checkRent() {
    System.out.println("대출현황");
    for (RentInfo rentInfo : rentList) {
      System.out.println(rentInfo.toString());
    }
  }

  @Override
  public String[] getMenus() {
    return menus;
  }
}

