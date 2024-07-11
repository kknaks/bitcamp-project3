package bitcamp.project3.command;

public class StoreCommand implements Command{
  public void execute(){
    System.out.println("Store command executed");
  }

  @Override
  public String[] getMenus() {
    return new String[0];
  }
}
