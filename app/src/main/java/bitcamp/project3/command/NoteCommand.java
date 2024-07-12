package bitcamp.project3.command;

import bitcamp.project3.util.Prompt;
import bitcamp.project3.vo.Guest;
import bitcamp.project3.vo.MemoInfo;

import java.time.LocalDate;
import java.util.List;

public class NoteCommand implements Command {
  List<Guest> guestList;

  public NoteCommand(List<Guest> guestList) {
    this.guestList = guestList;
  }

  public void execute() {
    while (true) {
      for (Guest guest : guestList) {
        System.out.println(guest.getType());
      }
      System.out.println(9);
      String command = Prompt.input("메모할 손놈?");
      if (command.equals("9")) {
        return;
      }
      try {
        int menuNo = Integer.parseInt(command);
        if (menuNo > guestList.size()) {
          System.out.println("없는손님");
        } else {
          String comment = Prompt.input("메모할 사항 :");
          MemoInfo memo = new MemoInfo();
          memo.setMemo(comment);
          memo.setWriteDate(LocalDate.now());
          Guest guest = guestList.get(menuNo - 1);
          guest.setMemo(memo);
          System.out.println("등록완료");
        }
      } catch (NumberFormatException e) {
        System.out.println("숫자로 메뉴 번호를 입력하세요.");
      }
    }
  }

  @Override
  public String[] getMenus() {
    return new String[0];
  }

}
