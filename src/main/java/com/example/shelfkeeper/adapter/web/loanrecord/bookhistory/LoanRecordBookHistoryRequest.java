package com.example.shelfkeeper.adapter.web.loanrecord.bookhistory;

import com.example.shelfkeeper.usecase.loanrecord.bookhistory.LoanRecordBookHistoryInputData;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

/**
 * 書籍の貸出履歴取得 API のリクエストパラメータ。
 *
 * @author itokanta
 */
public class LoanRecordBookHistoryRequest {
  /** 履歴を取得する書籍の識別子。 */
  @NotNull(message = "書籍IDは必須です")
  @Min(value = 1, message = "書籍IDは1以上で指定してください")
  private Integer bookId;

  public Integer getBookId() {
    return bookId;
  }

  public void setBookId(Integer bookId) {
    this.bookId = bookId;
  }

  /**
   * ユースケース入力データへ変換する。
   *
   * @return 書籍の貸出履歴取得の入力データ
   */
  public LoanRecordBookHistoryInputData toLoanRecordBookHistoryInputData() {
    return new LoanRecordBookHistoryInputData(bookId);
  }
}
