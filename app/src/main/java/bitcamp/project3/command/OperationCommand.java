package bitcamp.project3.command;

import bitcamp.project3.util.Prompt;
import bitcamp.project3.vo.BookInfo;
import bitcamp.project3.vo.RentInfo;
import bitcamp.project3.vo.StoreInfo;

import java.time.format.DateTimeFormatter;
import java.util.List;

public class OperationCommand implements Command {

  //String[] menus = {"재고조회", "서점가기", "대출현황"};
  //Map<Integer, MenuAction> menuMap = new HashMap<>();
  private List<BookInfo> bookList;
  private List<RentInfo> rentList;
  private StoreInfo storeInfo;

  public OperationCommand(List<BookInfo> bookList, List<RentInfo> rentList, StoreInfo storeInfo) {
    this.bookList = bookList;
    this.rentList = rentList;
    this.storeInfo = storeInfo;
  }

  //  public static void main(String[] args) {
  //    //    String s1 = "가가가";
  //    //    String s2 = "가가가가";
  //    //    String s3 = "가가가가가";
  //
  //    int width = 15; // 원하는 폭
  //    String s4 = "";
  //    for (int i = 0; i < 10; i++){
  //      s4 += "가";
  //      System.out.printf("%-" + (getAdjustedWidth(s4, width))+ "s|\n", s4);
  //      System.out.printf("%d \n",getAdjustedWidth(s4, width));
  //      System.out.printf("----------\n");
  //    }
  //  }
  private static int getAdjustedWidth(String s, int width) {
    int length = s.length();
    int nonAsciiCount = 0;

    for (char c : s.toCharArray()) {
      if (c > 127) {
        nonAsciiCount++;
      }
    }
    return width - ((2 * nonAsciiCount - 1) / 2);
  }

  private static String getCenteredString(String s, int totalWidth) {
    int length = s.length();
    int spaces = (totalWidth - length) / 2;
    int leftSpaces = spaces;
    int rightSpaces = totalWidth - length - leftSpaces;

    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < leftSpaces; i++) {
      sb.append(" ");
    }
    sb.append(s);
    for (int i = 0; i < rightSpaces; i++) {
      sb.append(" ");
    }
    return sb.toString();
  }

  public void execute(String menuName) {
    System.out.printf("[%s]\n", menuName);
    switch (menuName) {
      case "재고조회":
        this.checkStock();
        break;
      case "서점가기":
        this.bookStore();
        break;
      case "대출현황":
        this.checkRent();
        break;
    }
  }

  public void checkStock() {
    int strWidth = 12;
    System.out.println("재고현황");
    System.out.println("-----------------------------");
    System.out.printf("%-10s|%10s\n", "책제목", "보유권수");
    System.out.println("-----------------------------");
    for (BookInfo bookInfo : bookList) {
      System.out.printf("%-" + getAdjustedWidth(bookInfo.getBookName(), strWidth) + "s|",
          bookInfo.getBookName());
      System.out.printf("%10s권\n", bookInfo.getStock());
    }
    System.out.println("-----------------------------");
  }

  public void bookStore() {
    int strWidth = 12;
    System.out.println("서점");
    System.out.println("-----------------------------");
    System.out.printf("%-10s|%10s\n", "책제목", "판매가격");
    System.out.println("-----------------------------");
    for (BookInfo bookInfo : bookList) {
      System.out.printf("%-" + getAdjustedWidth(bookInfo.getBookName(), strWidth) + "s|",
          bookInfo.getBookName());
      System.out.printf("%10s원\n", bookInfo.getPrice());
    }
    System.out.println("-----------------------------");

    try {
      int menuNo = Prompt.inputInt("구매할 책(뒤로가기: 0)>");
      if (menuNo == 0) {
        return;
      }
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
    System.out.println("-----------------------------------------------------");
    System.out.printf("  고객구분  |");
    System.out.printf("   책제목   |");
    System.out.printf("   대여일   |");
    System.out.printf("   반납일   \n");
    System.out.println("-----------------------------------------------------");
    for (RentInfo rentInfo : rentList) {
      System.out.printf("%-" + getAdjustedWidth(rentInfo.getGuestType(), 11) + "s|",
          rentInfo.getGuestType());
      System.out.printf("%-" + getAdjustedWidth(rentInfo.getBookName(), 11) + "s|",
          rentInfo.getBookName());
      String startDateStr = rentInfo.getRentStartDate().format(DateTimeFormatter.ISO_LOCAL_DATE);
      System.out.printf(" %-" + getAdjustedWidth(startDateStr, 11) + "s|", startDateStr);
      String endDateStr = rentInfo.getRentEndDate().format(DateTimeFormatter.ISO_LOCAL_DATE);
      System.out.printf(" %-" + getAdjustedWidth(endDateStr, 11) + "s|", endDateStr);
      System.out.printf("%b", rentInfo.isBookReturn());

      System.out.println();
    }
  }

}

