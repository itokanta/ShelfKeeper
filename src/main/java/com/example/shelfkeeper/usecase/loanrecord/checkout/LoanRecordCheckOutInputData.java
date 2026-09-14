package com.example.shelfkeeper.usecase.loanrecord.checkout;

/**
 * 貸出記録登録ユースケースの入力データ。
 *
 * @author itokanta
 */
public class LoanRecordCheckOutInputData {
  /** 貸出利用者の識別子。 */
  private final Integer userId;
  /** 貸出書籍の識別子。 */
  private final Integer bookId;

  public LoanRecordCheckOutInputData(Integer userId, Integer bookId) {
    this.userId = userId;
    this.bookId = bookId;
  }

  public Integer getUserId() {
    return userId;
  }

  public Integer getBookId() {
    return bookId;
  }
}
