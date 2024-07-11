package bitcamp.project3.command;

public class NoteCommand implements Command{
  public void execute(){
    System.out.println("Note command executed");
  }

  @Override
  public String[] getMenus() {
    return new String[0];
  }

}
