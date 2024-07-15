package bitcamp.project3.command;

import bitcamp.project3.util.CreateRandom.RandomAction;
import bitcamp.project3.util.CreateRandom.RandomNum;
import bitcamp.project3.util.CreateRandom.RandomZero;
import bitcamp.project3.util.Prompt;
import bitcamp.project3.vo.*;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GuestCommand implements Command {

  static String bookName;
  List<Guest> guests;
  String[] menus = {"빌려준다", "거절한다"};
  List<BookInfo> bookInfoList;
  List<RentInfo> rentInfoList;
  StoreInfo storeInfo;
  Map<Integer, MenuAction> menuMap = new HashMap<>();
  RandomAction randomNum = new RandomNum();
  RandomAction randomZero = new RandomZero();



  public GuestCommand(List<BookInfo> bookInfoList, List<RentInfo> rentInfoList,
      StoreInfo storeInfo, List<Guest> guests) {
    this.bookInfoList = bookInfoList;
    this.rentInfoList = rentInfoList;
    this.storeInfo = storeInfo;
    this.guests = guests;
  }

  public void execute() {
    int guestRandomValue = randomNum.randomDice(guests.size());
    double checkLoss;
    final Guest guest = guests.get(guestRandomValue);
    menuMap.put(1, () -> accept(guest));
    menuMap.put(2, () -> reject(guest));

    System.out.println("-------------------------");
    System.out.printf("[%s] 손님이 입장하셨습니다.\n", guest.getType());
    System.out.printf("명성도:[%s] \t피로도:[%s] \t분실수:[%s] \t분실력:[%s]\n",
        guest.getReputation(), guest.getTiredness(), guest.getLossCount(), percentFormat(guest.getLossForce()));

    for (RentInfo rentInfo : rentInfoList){
      if (rentInfo.getGuestType().equals(guest.getType()) &&
          rentInfo.getRentEndDate().isEqual(storeInfo.getDate()) &&
          !rentInfo.isBookReturn()){
          checkLoss = randomZero.randomDice(guest.getLossForce());

          BookInfo book = getBookName(rentInfo.getBookName());
          if (checkLoss == 0){
            if (book != null){
              System.out.printf("[%s] 손님이 [%s] 책을 분실했습니다.\n", guest.getType(), book.getBookName());
              book.setStock(book.getStock() - 1);
              storeInfo.setTiredness(storeInfo.getTiredness() + guest.getTiredness());
              guest.setLossCount(guest.getLossCount() + 1);
            }else{
              System.out.println("[%s]는 없는 책입니다");
            }
          }else{
            System.out.printf("[%s] 손님이 [%s] 책을 반납했습니다.\n", guest.getType(), book.getBookName());
            book.setStock(book.getStock() + 1);
            rentInfo.setBookReturn(true);

            storeInfo.setTiredness(storeInfo.getTiredness() - guest.getTiredness());
          }
      }
    }
      guest.setRentPeriod(randomNum.randomDice());
      bookName = bookInfoList.get(randomNum.randomDice(bookInfoList.size())).getBookName();
      System.out.printf("\n[%s] >> [%s] [%d]일 동안 빌릴 수 있을까요?\n", guest.getType(), bookName, guest.getRentPeriod());
      System.out.println("-------------------------");

      executeMenu();
  }


  private void accept(Guest guest) {
    BookInfo book = getBook();

    if (book.getStock() == 0) {
      System.out.println("-------------------------");
      System.out.printf("[주인놈] >> 현재 [%s] 책 재고가 없습니다. 다음에 찾아주세요.\n", book.getBookName());
      System.out.println("-------------------------");
      storeInfo.setReputation(storeInfo.getReputation() - guest.getReputation());
      storeInfo.setTiredness(storeInfo.getTiredness() + guest.getRentPeriod());
    } else {
      LocalDate rentPeriod = storeInfo.getDate().plusDays(guest.getRentPeriod());
      System.out.println("-------------------------");
      System.out.printf("[%s] : [%s] 손님이 [%s] 책을 대여했습니다.\n"
          ,storeInfo.getDate(), guest.getType(),book.getBookName());
      System.out.printf("[%s] 재고: [%d]권\n", book.getBookName(), book.getStock());

      RentInfo rentInfo = new RentInfo();
      rentInfo.setGuestType(guest.getType());
      rentInfo.setBookName(book.getBookName());
      rentInfo.setRentStartDate(storeInfo.getDate());
      rentInfo.setRentEndDate(rentPeriod);
      rentInfoList.add(rentInfo);
      book.setStock(book.getStock() - 1);

      System.out.printf("\n[주인놈] >> 오늘이 [%s]이니까...[%s]일..후면..[%s]일 까지 반납하세요!\n"
          ,storeInfo.getDate(), guest.getRentPeriod(), rentPeriod);

      storeInfo.setAccount(storeInfo.getAccount() + book.getPrice());
      storeInfo.setReputation(storeInfo.getReputation() + guest.getReputation());
      storeInfo.setTiredness(storeInfo.getTiredness() + guest.getTiredness());
    }
  }

  private void reject(Guest guest) {
    System.out.println("[주인놈] >> 죄송합니다. 찾으시는 책은 없습니다.\n");

    storeInfo.setReputation(storeInfo.getReputation() - guest.getReputation());
    storeInfo.setTiredness(storeInfo.getTiredness() + guest.getTiredness());
  }



  private String percentFormat(int lossForce){
    double lossRate = lossForce / 10.0;
    NumberFormat percentFormat = NumberFormat.getPercentInstance();
    percentFormat.setMinimumFractionDigits(0);
    return percentFormat.format(lossRate);
  }


  private BookInfo getBook() {
    for (BookInfo bookInfo : bookInfoList) {
      if (bookInfo.getBookName().equals(bookName)) {
        return bookInfo;
      }
    }
    return null;
  }

  private BookInfo getBookName(String name) {
    for (BookInfo bookInfo : bookInfoList) {
      if (bookInfo.getBookName().equals(name)) {
        return bookInfo;
      }
    }
    return null;
  }


  private void executeMenu(){
    int menuNo;

    printMenus();
    while (true) {
      try {
        menuNo = Prompt.inputInt(">");
        if (menuNo == 0) {
          return;
        }
        if (getMenuTitle(menuNo) == null) {
          System.out.println("유효한 메뉴 번호가 아닙니다.");
          continue;
        }
        MenuAction action = menuMap.get(menuNo);

        if (action != null) {
          action.execute();
        }
        break;
      } catch (NumberFormatException exception) {
        System.out.println("숫자만 입력해 주세요.");
      }
    }
  }


  @Override
  public String[] getMenus() {
    return menus;
  }
}
