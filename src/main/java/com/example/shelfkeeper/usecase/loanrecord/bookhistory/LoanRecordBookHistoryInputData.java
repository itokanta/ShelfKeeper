package com.example.shelfkeeper.usecase.loanrecord.bookhistory;

/**
 * 書籍の貸出履歴取得ユースケースの入力データ。
 *
 * @author itokanta
 */
public class LoanRecordBookHistoryInputData {
  /** 取得対象の書籍の識別子。 */
  private final Integer bookId;

  public LoanRecordBookHistoryInputData(Integer bookId) {
    this.bookId = bookId;
  }

  public Integer getBookId() {
    return bookId;
  }
}
