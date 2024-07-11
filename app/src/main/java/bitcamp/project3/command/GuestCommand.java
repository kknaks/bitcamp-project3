package bitcamp.project3.command;

import bitcamp.project3.util.Prompt;
import bitcamp.project3.vo.Grandpa;
import bitcamp.project3.vo.Guest;
import bitcamp.project3.vo.Kid;
import bitcamp.project3.vo.NoJob;
import bitcamp.project3.vo.Student;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class GuestCommand implements Command {

  String[] menus = {"특징보기", "대출정보", "손님호출"};
  private final Random random = new Random();
  int randomValue = random.nextInt(4); // 0 ~ 3

  Guest[] guests = {
      new Kid(new ArrayList<>()),
      new Student(new ArrayList<>()),
      new NoJob(new ArrayList<>()),
      new Grandpa(new ArrayList<>())
  };

  Guest guest = guests[randomValue];
  Map<Integer, MenuAction> menuMap = new HashMap<>();

  public GuestCommand() {
    menuMap.put(1, () -> viewInfo(guest));
    menuMap.put(2, () -> bookRental(guest));
    menuMap.put(3, () -> call(guest));
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

  public void viewInfo(Guest guest){
    System.out.println(guest.toString(guest.getType()));
  }

  public void bookRental(Guest guest){
    System.out.printf("%s 손님의 도서 대출\n", guest.getType());
  }

  public void call(Guest guest){
    System.out.printf("%s 손님 일로와보슈!!\n", guest.getType());
  }

  @Override
  public String[] getMenus() {
    return menus;
  }


}
