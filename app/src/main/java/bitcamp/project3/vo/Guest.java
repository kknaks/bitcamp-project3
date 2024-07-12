package bitcamp.project3.vo;

import java.util.List;

// Kid (어린아이)
// Student (학생)
// NoJob (백수)
// Grandpa (할아버지)
public interface Guest {

  String getType();

  void setType(String type);

  List<MemoInfo> getMemos();

  void setMemo(MemoInfo memo);

  int getLossForce();

  int getReputation();

  List<RentInfo> getRentInfos();

  void setRentInfos(RentInfo rentInfo);

  List<BookInfo> getBookInfos();

  int getRentPeriod();

  void setRentPeriod(int rentPeriod);

  String toString(String guest);
}
