package com.example.shelfkeeper.adapter.web.loanrecord.checkout;

import com.example.shelfkeeper.usecase.loanrecord.checkout.LoanRecordCheckOutInputData;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

/**
 * 貸出記録登録 API のリクエストボディ。
 *
 * @author itokanta
 */
public class LoanRecordCheckOutRequest {
  /** 貸出利用者の識別子。 */
  @NotNull(message = "ユーザーIDは必須です")
  @Min(value = 1, message = "ユーザーIDは1以上で指定してください")
  private Integer userId;

  /** 貸出書籍の識別子。 */
  @NotNull(message = "書籍IDは必須です")
  @Min(value = 1, message = "書籍IDは1以上で指定してください")
  private Integer bookId;

  public Integer getUserId() {
    return userId;
  }

  public void setUserId(Integer userId) {
    this.userId = userId;
  }

  public Integer getBookId() {
    return bookId;
  }

  public void setBookId(Integer bookId) {
    this.bookId = bookId;
  }

  /**
   * ユースケース入力データへ変換する。
   *
   * @return 貸出記録登録の入力データ
   */
  public LoanRecordCheckOutInputData toLoanRecordCheckOutInputData() {
    return new LoanRecordCheckOutInputData(userId, bookId);
  }
}
