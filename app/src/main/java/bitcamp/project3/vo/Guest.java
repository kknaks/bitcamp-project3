package bitcamp.project3.vo;

import java.awt.print.Book;
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

    List<BookInfo> getBookInfos();

    int getRentPeriod();

    void setRentPeriod(int rentPeriod);

    void setBookInfos(BookInfo bookInfo);

    String toString(String guest);


}
