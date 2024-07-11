package bitcamp.project3.command;

public class BookCommand implements Command {
 public void execute(){
   System.out.println("Book command");
 }

    @Override
    public String[] getMenus() {
        return new String[0];
    }
}
