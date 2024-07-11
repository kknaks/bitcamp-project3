package bitcamp.project3.command;

public class DayOverCommand implements Command{
  public void execute(){
    System.out.println("Day Over");
  }

  @Override
  public String[] getMenus() {
    return new String[0];
  }
}
