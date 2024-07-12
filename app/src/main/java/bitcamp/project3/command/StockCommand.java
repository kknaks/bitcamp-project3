package bitcamp.project3.command;

import bitcamp.project3.vo.BookInfo;
import java.util.List;

public class StockCommand implements Command {
  private List<BookInfo> bookList;

  public StockCommand(List<BookInfo> bookList) {
    this.bookList = bookList;
  }

  public void execute(){
    System.out.println("Stock command executed");
    for(BookInfo book : bookList){
      System.out.printf("%s : %d\n", book.getBookName(),book.getStock());
    }
  }

  @Override
  public String[] getMenus() {
    return new String[0];
  }
}
