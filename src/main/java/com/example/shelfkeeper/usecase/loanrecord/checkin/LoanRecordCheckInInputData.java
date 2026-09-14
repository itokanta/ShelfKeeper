package com.example.shelfkeeper.usecase.loanrecord.checkin;

/**
 * 貸出記録返却ユースケースの入力データ。
 *
 * @author itokanta
 */
public class LoanRecordCheckInInputData {
  /** 返却対象の貸出記録の識別子。 */
  private final Integer id;

  public LoanRecordCheckInInputData(Integer id) {
    this.id = id;
  }

  public Integer getId() {
    return id;
  }
}
