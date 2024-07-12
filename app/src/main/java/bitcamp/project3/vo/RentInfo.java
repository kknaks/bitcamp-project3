package bitcamp.project3.vo;

import java.time.LocalDate;

public class RentInfo {
  private String guestType;
  private String bookName;
  private LocalDate rentStartDate;
  private LocalDate rentEndDate;
  private boolean bookReturn = false;

  public boolean isBookReturn() {
    return bookReturn;
  }

  public String getGuestType() {
    return guestType;
  }

  public void setGuestType(String guestType) {
    this.guestType = guestType;
  }

  public void setBookReturn(boolean bookReturn) {
    this.bookReturn = bookReturn;
  }

  public String getBookName() {
    return bookName;
  }

  public void setBookName(String bookName) {
    this.bookName = bookName;
  }

  public LocalDate getRentStartDate() {
    return rentStartDate;
  }

  public void setRentStartDate(LocalDate rentStartDate) {
    this.rentStartDate = rentStartDate;
  }

  public LocalDate getRentEndDate() {
    return rentEndDate;
  }

  public void setRentEndDate(LocalDate rentEndDate) {
    this.rentEndDate = rentEndDate;
  }
}
