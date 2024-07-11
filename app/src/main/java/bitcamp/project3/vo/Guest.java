package bitcamp.project3.vo;

import java.util.List;

// Kid (어린아이)
// Student (학생)
// NoJob (백수)
// Grandpa (할아버지)
public interface Guest {


    String getType();

    void setType(String type);

    List<String> getMemos();

    void setMemo(String memo);

    int getLossForce();

    int getReputation();


    String toString(String guest);
}
