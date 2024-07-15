package bitcamp.project3.menu;

import bitcamp.project3.command.Command;
import bitcamp.project3.command.GuestCommand;
import bitcamp.project3.util.CreateRandom;
import bitcamp.project3.util.Prompt;
import bitcamp.project3.vo.Guest;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Stack;

import static bitcamp.project3.App.bookList;

public class MenuGroup extends AbstractMenu {
  Command command;
  CreateRandom.RandomAction randomNum = new CreateRandom.RandomNum();
  private MenuGroup parent;
  private Stack<String> menuPath;
  private ArrayList<Menu> children = new ArrayList<>();
  private String exitMenuTitle = "이전";

  public MenuGroup(String title) {
    super(title);
    menuPath = new Stack<>();
  }

  public MenuGroup(String title, Command command) {
    super(title);
    this.command = command;
    menuPath = new Stack<>();
  }

  @Override
  public void execute() {
    menuPath.push(title);
    while (true) {
      printUI(title);
      printMenus();
      String command = Prompt.input("%s>", getMenuPathTitle());
      if (command.equals("menu")) {
        printMenus();
        continue;
      } else if (command.equals("0")) { // 이전 메뉴 선택
        menuPath.pop();
        return;
      }
      try {
        int menuNo = Integer.parseInt(command);
        Menu menu = getMenu(menuNo - 1);
        if (menu == null) {
          System.out.println("유효한 메뉴 번호가 아닙니다.");
          continue;
        }
        menu.execute();
      } catch (NumberFormatException ex) {
        System.out.println("숫자로 메뉴 번호를 입력하세요.");
      }
    }
  }

  public void add(Menu child) {
    if (child instanceof MenuGroup) {
      ((MenuGroup) child).setParent(this);
    }
    children.add(child);
  }

  public void remove(Menu child) {
    children.remove(child);
  }

  public void setExitMenuTitle(String title) {
    this.exitMenuTitle = title;
  }

  private void setParent(MenuGroup parent) {
    this.parent = parent;
    this.menuPath = parent.menuPath;
  }

  private void printUI(String title) {
    if (title.equals("손님받기")) {
      ((GuestCommand) command).preExecute();
      Guest guest = ((GuestCommand) command).getGuest();
      String bookName = ((GuestCommand) command).getBookName();

      System.out.println("-------------------------");
      System.out.printf("[%s] 손님이 입장하셨습니다.\n", guest.getType());
      System.out.printf("명성도:[%s] \t피로도:[%s] \t분실수:[%s] \t분실력:[%s]\n", guest.getReputation(),
          guest.getTiredness(), guest.getLossCount(), percentFormat(guest.getLossForce()));

      guest.setRentPeriod(randomNum.randomDice());
      bookName = bookList.get(randomNum.randomDice(bookList.size())).getBookName();
      System.out.printf("\n[%s] >> [%s] [%d]일 동안 빌릴 수 있을까요?\n", guest.getType(), bookName,
          guest.getRentPeriod());
      System.out.println("-------------------------");
    }
  }

  private void printMenus() {
    System.out.printf("[%s]\n", title);
    int i = 1;
    for (Menu menu : children) {
      System.out.printf("%d. %s\n", i++, menu.getTitle());

    }
    System.out.printf("0. %s\n", exitMenuTitle);
  }

  private String getMenuPathTitle() {
    StringBuilder strBuilder = new StringBuilder();
    for (int i = 0; i < menuPath.size(); i++) {
      if (strBuilder.length() > 0) {
        strBuilder.append("/");
      }
      strBuilder.append(menuPath.get(i));
    }
    return strBuilder.toString();
  }

  private Menu getMenu(int index) {
    if (index < 0 || index >= children.size()) {
      return null;
    }
    return children.get(index);
  }

  private String percentFormat(int lossForce) {
    double lossRate = lossForce / 10.0;
    NumberFormat percentFormat = NumberFormat.getPercentInstance();
    percentFormat.setMinimumFractionDigits(0);
    return percentFormat.format(lossRate);
  }
}
