package bitcamp.project3.vo;

import java.time.LocalDate;

public class StoreInfo {
  private int reputation;
  private int account;
  private int tiredness;
  private LocalDate date;

  public StoreInfo() {
    this.reputation = 50;
    this.account = 50;
    this.tiredness = 50;
    this.date = LocalDate.of(2024, 1, 1);

  }

  public LocalDate getDate() {
    return date;
  }

  public void setDate(LocalDate date) {
    this.date = date;
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
