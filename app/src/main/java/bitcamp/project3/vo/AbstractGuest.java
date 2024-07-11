package bitcamp.project3.vo;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractGuest implements Guest{

    String type;
    List<String> memos = new ArrayList<>();
    int lossForce;
    int reputation;

    @Override
    public String getType() {
        return this.type;
    }

    @Override
    public void setType(String type) {
        this.type = type;
    }

    @Override
    public List<String> getMemos() {
        return memos;
    }

    @Override
    public void setMemo(String memo) {
        this.memos.add(memo);
    }

    @Override
    public int getLossForce() {
        return lossForce;
    }

    @Override
    public int getReputation() {
        return reputation;
    }

    @Override
    public String toString(String guest) {
        return guest +"{" +
            "type='" + type + '\'' +
            ", memos=" + memos +
            ", lossForce=" + lossForce +
            ", reputation=" + reputation +
            '}';
    }
}
