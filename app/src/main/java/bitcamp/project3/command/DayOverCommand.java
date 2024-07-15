package bitcamp.project3.command;

import bitcamp.project3.util.Prompt;
import bitcamp.project3.vo.StoreInfo;

public class DayOverCommand implements Command {
  StoreInfo storeInfos;

  public DayOverCommand(StoreInfo storeInfos) {
    this.storeInfos = storeInfos;
  }

  @Override
  public void execute(String menuName) {
    System.out.println("------영업종료--------");
    System.out.println("------정산내역--------");
    //정산내역 정리하기
    System.out.println("대출권수 : ");
    // 명성 몇 +? 피로도 몇 +? 자금 몇 +?
    Prompt.input("계속하려면 엔터를 누르세요.");
    //분실한 책 출력
    //대출 목록에서 endDate도래 한것들을 선택 반납여부 확인
    //반납하면 +1, 분실하면 -1
    //도래된것은 rentlist에서 제거
    //북리스트에서 분실한 책 수 만큼 빼기
    System.out.println("-------------------------");
    storeInfos.setDate(storeInfos.getDate().plusDays(1));
  }
}
