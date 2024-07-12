package bitcamp.project3.vo;

public class StoreInfo {
  private int reputation;
  private int account;
  private int tiredness;

  public StoreInfo(int reputation, int account, int tiredness) {
    this.reputation = reputation;
    this.account = account;
    this.tiredness = tiredness;
  }

  public int getReputation() {
    return reputation;
  }

  public void setReputation(int reputation) {
    this.reputation = reputation;
  }

  public int getAccount() {
    return account;
  }

  public void setAccount(int account) {
    this.account = account;
  }

  public int getTiredness() {
    return tiredness;
  }

  public void setTiredness(int tiredness) {
    this.tiredness = tiredness;
  }
}
