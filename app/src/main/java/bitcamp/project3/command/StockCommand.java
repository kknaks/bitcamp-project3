package bitcamp.project3.command;

public class StockCommand implements Command {
  public void execute(){
    System.out.println("Stock command executed");
  }

  @Override
  public String[] getMenus() {
    return new String[0];
  }
}
