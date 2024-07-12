package bitcamp.project3.command;

import bitcamp.project3.util.Prompt;
import bitcamp.project3.vo.BookInfo;
import bitcamp.project3.vo.Grandpa;
import bitcamp.project3.vo.Guest;
import bitcamp.project3.vo.Kid;
import bitcamp.project3.vo.MemoInfo;
import bitcamp.project3.vo.NoJob;
import bitcamp.project3.vo.RentInfo;
import bitcamp.project3.vo.Student;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class GuestCommand implements Command {

  String[] menus = {"손님유형", "도서정보","대여정보", "메모정보", "호출하기"};

  private final Random random = new Random();
  int randomValue = random.nextInt(4); // 0 ~ 3

  Guest[] guests = {
      new Kid(),
      new Student(),
      new NoJob(),
      new Grandpa()
  };

  Guest guest = guests[randomValue];
  Map<Integer, MenuAction> menuMap = new HashMap<>();

  public GuestCommand() {
    menuMap.put(1, () -> guestInfo(guest));
    menuMap.put(2, () -> bookInfo(guest));
    menuMap.put(3, () -> rentInfo(guest));
    menuMap.put(4, () -> memoInfo(guest));
    menuMap.put(5, () -> callGuest(guest));
  }

  public void execute(){
    int menuNo;

    System.out.println("---------------------------------------------------------------");
    System.out.printf("띠링\uD83C\uDFB6 [%s] 손님이 입장하셨습니다.\n", guest.getType());
    System.out.println("손님이 왔다... 나의 반응은?");
    System.out.println("---------------------------------------------------------------");

    printMenus();

    while (true){
      try {
        menuNo = Prompt.inputInt(">");
        if (menuNo == 0){
          return;
        }
        if (getMenuTitle(menuNo) == null){
          System.out.println("유효한 메뉴 값이 아닙니다.");
          continue;
        }
        MenuAction action = menuMap.get(menuNo);
        if (action != null){
          action.execute();
        }
      }catch (NumberFormatException exception){
        System.out.println("숫자만 입력해 주세요.");
      }
    }
  }

  public void guestInfo(Guest guest){
    System.out.println(guest.toString(guest.getType()));
  }

  public void rentInfo(Guest guest){
    System.out.printf("%s 손님의 대여 정보\n", guest.getType());
    for (RentInfo rent : guest.getRentInfos()){
      System.out.println(rent.getBookName());
    }
  }

  public void bookInfo(Guest guest){
    System.out.printf("%s 손님의 도서 정보\n", guest.getType());
    for (BookInfo book : guest.getBookInfos()){
      System.out.println(book.getBookName());
    }
  }

  public void callGuest(Guest guest){
    System.out.println("호출");
    System.out.printf("%s 손님 일로와보슈!!\n", guest.getType());
  }

  public void memoInfo(Guest guest){
    System.out.printf("%s 손님의 메모 정보\n", guest.getType());
    for (MemoInfo memo : guest.getMemos()){
      System.out.println(memo.getMemo() + memo.getWriteDate());
    }
  }


  @Override
  public String[] getMenus() {
    return menus;
  }


}
