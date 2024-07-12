package bitcamp.project3.vo;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractGuest implements Guest{

    protected String type;
    protected List<MemoInfo> memos;
    protected List<RentInfo> rentInfos;
    protected List<BookInfo> bookInfos;
    protected int lossForce;
    protected int reputation;
    protected int rentPeriod;

    protected AbstractGuest(String type, int lossForce, int reputation, int rentPeriod) {
        this.type = type;
        this.memos = new ArrayList<>();
        this.rentInfos = new ArrayList<>();
        this.bookInfos = new ArrayList<>();
        this.lossForce = lossForce;
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
    public void setRentInfos(RentInfo rentInfo) {
        this.rentInfos.add(rentInfo);
    }

    @Override
    public void setBookInfos(BookInfo bookInfo) {
        this.bookInfos.add(bookInfo);
    }

    @Override
    public List<RentInfo> getRentInfos() {
        return rentInfos.stream().toList();
    }

    @Override
    public List<BookInfo> getBookInfos() {
        return bookInfos.stream().toList();
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
    public int getLossForce() {
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
    public String toString(String guest) {
        return type +
            ": 메모=" + memos +
            ", 대출정보=" + rentInfos +
            ", 도서정보=" + bookInfos;
    }

}
