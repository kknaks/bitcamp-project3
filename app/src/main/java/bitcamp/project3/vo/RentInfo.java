package bitcamp.project3.vo;

import java.time.LocalDate;

public class RentInfo {
  private String bookName;
  private LocalDate retunDate;

  public String getBookName() {
    return bookName;
  }

  public void setBookName(String bookName) {
    this.bookName = bookName;
  }
}
