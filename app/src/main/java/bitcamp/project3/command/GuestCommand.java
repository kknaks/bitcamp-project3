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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class GuestCommand implements Command {

  // 책은 맵으로 받기 -> 키 : 책이름 , 값 : 수량

  String[] menus = {"빌려준다", "거절한다"};

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
    menuMap.put(1, () -> accept(guest));
    menuMap.put(2, () -> reject(guest));
  }

  public void execute(){
    int menuNo;

    /*
손님이 입장하셨습니다
무슨 책 빌릴 수 있나요
*
* 1. 빌려준다
* 2. 안빌려준다
* 3. 손님 정보 확인
* */
    System.out.println("---------------------------------------------------------------");
    System.out.printf("띠링\uD83C\uDFB6 [%s] 손님이 입장하셨습니다.\n", guest.getType());
    System.out.println("책 목록 보여주기");
    System.out.printf("[%s] >> [나루토] 책 빌릴 수 있을까요?\n", guest.getType());
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

  public void accept(Guest guest){
    System.out.println("승락");
  }

  public void reject(Guest guest){
    System.out.println("거절");
  }




/*
* >> [초등학생] : [블리츠] 있나요
1. 빌려준다 [명성 +10 , 분실 O, 피로도 +10]
2. 안빌려준다 [명성 -20 , 분실 ㅌ, 피로도 +5]
* */
  public void callGuest(Guest guest){
    String bookTitle = "나루토";

    System.out.println("---------------------------------------------------------------");
    System.out.printf("[%s] >> [%s] 있나요?\n", guest.getType(), bookTitle);
    System.out.printf("손님이 [%s] 책을 찾는다...\n", bookTitle);
    System.out.println("---------------------------------------------------------------");

    System.out.println("1. 빌려준다 [명성+10, 분실 O, 피로도+10]");
    System.out.println("2. 안 빌려준다 [명성-10, 분실 X, 피로도+5]");

    while (true){
      try {
        int menuNo = Prompt.inputInt(">");
        switch (menuNo){
          case 1:
            LocalDate rentPeriod = LocalDate.now().plusDays(guest.getRentPeriod());

            System.out.printf("[%s]일 까지 반납하세요!\n", rentPeriod);

            RentInfo rentInfo = new RentInfo();
            rentInfo.setBookName(bookTitle);
            rentInfo.setRentStartDate(LocalDate.now());
            rentInfo.setRentEndDate(rentPeriod);
            rentInfos.add(rentInfo);
            break;
          case 2:
            System.out.println("안 빌려줄거야 명성하락, 피로도업");
            break;
          default:
            System.out.println("유효한 값이 아닙니다.");
            continue;
        }
        return;
      }catch (NumberFormatException exception){
        System.out.println("숫자만 입력해 주세요.");
      }
    }
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
