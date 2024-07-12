package bitcamp.project3.command;

import bitcamp.project3.util.Prompt;
import bitcamp.project3.vo.StoreInfo;

public class DayOverCommand implements Command {
  StoreInfo storeInfos;

  public DayOverCommand(StoreInfo storeInfos) {
    this.storeInfos = storeInfos;
  }

  @Override
  public void execute() {
    System.out.println("------영업종료--------");
    System.out.println("------정산내역--------");
    System.out.println("대출권수 : ");
    System.out.println("수익금 : ");
    Prompt.input("계속하려면 엔터를 누르세요.");
    System.out.println("-------------------------");
    storeInfos.setDate(storeInfos.getDate().plusDays(1));
  }

  @Override
  public String[] getMenus() {
    return new String[0];
  }
}
