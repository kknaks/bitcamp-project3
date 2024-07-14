package bitcamp.project3.vo;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractGuest implements Guest{

    protected String type;
    protected List<MemoInfo> memos;
    protected double lossForce;
    protected int lossCount;
    protected int reputation;
    protected int rentPeriod;

    protected AbstractGuest(String type, double lossForce, int reputation, int rentPeriod) {
        this.type = type;
        this.memos = new ArrayList<>();
        this.lossForce = lossForce;
        this.lossCount = 0;
        this.reputation = reputation;
        this.rentPeriod = rentPeriod;
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
    public List<MemoInfo> getMemos() {
        return memos.stream().toList();
    }

    @Override
    public void setMemo(MemoInfo memo) {
        memos.add(memo);
    }

    @Override
    public double getLossForce() {
        return lossForce;
    }

    @Override
    public int getReputation() {
        return reputation;
    }

    @Override
    public int getRentPeriod() {
        return this.rentPeriod;
    }

    @Override
    public void setRentPeriod(int rentPeriod) {
        this.rentPeriod = rentPeriod;
    }

    @Override
    public int getLossCount() {
        return this.lossCount;
    }

    @Override
    public void setLossCount(int lossCount) {
        this.lossCount = lossCount;
    }
}
