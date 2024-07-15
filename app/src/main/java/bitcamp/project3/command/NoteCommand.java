package bitcamp.project3.command;

import bitcamp.project3.util.Prompt;
import bitcamp.project3.vo.Guest;
import bitcamp.project3.vo.MemoInfo;
import bitcamp.project3.vo.StoreInfo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class NoteCommand implements Command {
  Map<Integer, MenuAction> menuMap = new HashMap<>();
  List<Guest> guestList;
  StoreInfo storeInfos;

  public NoteCommand(List<Guest> guestList, StoreInfo storeInfos) {
    this.guestList = guestList;
    this.storeInfos = storeInfos;
  }

  public void execute() {
    for (int i = 0; i < guestList.size(); i++) {
      Guest guest = guestList.get(i);
//      menuMap.put(i + 1, () -> note(guest));
    }
    while (true) {
      printMenus();

      int menuNo = Prompt.inputInt("메모할 손놈");
      if (menuNo == 0) {
        return;
      }
      if (getMenuTitle(menuNo) == null) {
        System.out.println("없는 손님.");
        continue;
      }
      menuMap.get(menuNo).execute();
    }
  }
//
//  public void note(Guest guest) {
//    String comment = Prompt.input("메모할 사항 :");
//    MemoInfo memo = new MemoInfo();
//    memo.setMemo(comment);
//    memo.setWriteDate(storeInfos.getDate());
//    guest.setMemo(memo);
//    System.out.println("등록완료");
//  }

  @Override
  public String[] getMenus() {
    String[] menus = new String[guestList.size()];
    for (int i = 0; i < guestList.size(); i++) {
      menus[i] = guestList.get(i).getType();
    }
    return menus;
  }
}
