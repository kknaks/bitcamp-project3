package bitcamp.project3.command;

import bitcamp.project3.util.Prompt;
import bitcamp.project3.vo.BookInfo;
import bitcamp.project3.vo.StoreInfo;

import java.util.List;

public class StoreCommand implements Command {
  private List<BookInfo> bookList;
  private StoreInfo storeInfo;

  public StoreCommand(List<BookInfo> bookList, StoreInfo storeInfos) {
    this.bookList = bookList;
    this.storeInfo = storeInfos;
  }

  public void execute() {
    while (true) {
      System.out.println("Store command executed");
      for (BookInfo book : bookList) {
        System.out.printf("%s,%d권 --- %d\n", book.getBookName(), book.getStock(), book.getPrice());
      }
      System.out.println("9");

      String command = Prompt.input("책을 고르세요");
      if (command.equals("9")) {
        return;
      }
      try {
        int menuNo = Integer.parseInt(command);
        if (menuNo > bookList.size()) {
          System.out.println("없는책");
        } else {
          BookInfo book = bookList.get(menuNo - 1);
          int change = storeInfo.getAccount() - book.getPrice();
          if (change < 0) {
            System.out.println("돈없음");
            continue;
          }
          storeInfo.setAccount(change);
          bookList.get(menuNo - 1).setStock(book.getStock() + 1);
          System.out.println("구매완료");
        }
      } catch (NumberFormatException e) {
        System.out.println("숫자로 메뉴 번호를 입력하세요.");
      }
    }
  }

  @Override
  public String[] getMenus() {
    return new String[0];
  }
}
