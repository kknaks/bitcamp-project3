package bitcamp.project3.vo;

import java.time.LocalDate;

public class MemoInfo {

    String memo;
    LocalDate writeDate;

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public LocalDate getWriteDate() {
        return writeDate;
    }

    public void setWriteDate(LocalDate writeDate) {
        this.writeDate = writeDate;
    }
}
