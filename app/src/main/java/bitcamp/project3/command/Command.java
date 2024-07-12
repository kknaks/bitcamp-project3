package bitcamp.project3.command;

import bitcamp.project3.vo.RentInfo;
import java.util.ArrayList;
import java.util.List;

public interface Command {

  List<RentInfo> rentInfos = new ArrayList<>();

  void execute();
  String[] getMenus();

  default void printMenus() {
    String[] menus = getMenus();
    for (int i = 0; i < menus.length; i++) {
      System.out.printf("%d. %s\n", (i + 1), menus[i]);
    }
    System.out.println("0. 이전");
  }

  default String getMenuTitle(int menuNo) {
    String[] menus = getMenus();
    return isValidateMenu(menuNo) ? menus[menuNo - 1] : null;
  }

  default boolean isValidateMenu(int menuNo) {
    String[] menus = getMenus();
    return menuNo >= 1 && menuNo <= menus.length;
  }

  default List<RentInfo> getRentInfos(){
    return rentInfos;
  }

  default void addRent(RentInfo rentInfo){
    rentInfos.add(rentInfo);
  }



}
