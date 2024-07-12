package bitcamp.project3.command;

import bitcamp.project3.util.Prompt;
import bitcamp.project3.vo.BookInfo;
import bitcamp.project3.vo.StoreInfo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StockCommand implements Command {

  String[] menus = {"재고조회", "서점가기"};
  Map<Integer, MenuAction> menuMap = new HashMap<>();
  private List<BookInfo> bookList;
  private StoreInfo storeInfo;

  public StockCommand(List<BookInfo> bookList, StoreInfo storeInfo) {
    this.bookList = bookList;
    this.storeInfo = storeInfo;
    menuMap.put(1, this::checkStock);
    menuMap.put(2, this::bookStore);
  }

  public void execute() {
    int menuNo;
    printMenus();
    while (true) {
      try {
        menuNo = Prompt.inputInt(">");
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
    System.out.println("재고현황");
    for (int i = 0; i < bookList.size(); i++) {
      BookInfo bookInfo = bookList.get(i);
      System.out.println(bookInfo.toString());
    }
  }

  public void bookStore() {
    System.out.println("서점");
    for (int i = 0; i < bookList.size(); i++) {
      BookInfo bookInfo = bookList.get(i);
      System.out.println(bookInfo.toString());
    }
    try {
      int menuNo = Prompt.inputInt(">");
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
        System.out.println("구매완료");
      }
    } catch (NumberFormatException exception) {
      System.out.println("숫자만 입력해 주세요.");
    }

  }

  @Override
  public String[] getMenus() {
    return menus;
  }
}

