package bitcamp.project3.vo;

public class BookInfo {
  private String bookName;
  private int stock;
  private int price;

  public BookInfo(String bookName, int stock, int price) {
    this.bookName = bookName;
    this.stock = stock;
    this.price = price;
  }
  public int getPrice() {
    return price;
  }

  public void setPrice(int price) {
    this.price = price;
  }

  public String getBookName() {
    return bookName;
  }

  public int getStock() {
    return stock;
  }

  public void setStock(int stock) {
    this.stock = stock;
  }

  public void setBookName(String bookName) {
    this.bookName = bookName;
  }
  //중첩클래스 사용 ?

}
