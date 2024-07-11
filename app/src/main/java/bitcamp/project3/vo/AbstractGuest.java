package bitcamp.project3.vo;

import java.util.List;

public abstract class AbstractGuest implements Guest{

    protected String type;
    protected List<String> memos;
    protected int lossForce;
    protected int reputation;

    protected AbstractGuest(String type, List<String> memos, int lossForce, int reputation) {
        this.type = type;
        this.memos = memos;
        this.lossForce = lossForce;
        this.reputation = reputation;
    }

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
        return memos.stream().toList();
    }

    @Override
    public void setMemo(String memo) {
        memos.add(memo);
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
