package bitcamp.project3.menu;

import bitcamp.project3.command.Command;

public class MenuItem extends AbstractMenu {
  Command command;

  public MenuItem(String title, Command command) {
    super(title);
    this.command = command;
  }

  public void MenuItem(Command command) {
    this.command = command;
  }

  @Override
  public void execute() {
    if (command != null) {
      command.execute(title);
    } else {
      System.out.println("execute");
    }
  }

  //  public void printUI(String title){
  //    if title.equals("빌려준다"){
  //      Guest guest = ((GuestCommand) command).getGuest();
  //      String bookName = ((GuestCommand) command).getBookName();
  //    }
  //  }
}
