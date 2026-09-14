package com.example.shelfkeeper.usecase.loanrecord.checkout;

/**
 * 貸出記録を登録するユースケース。
 *
 * @author itokanta
 */
public interface LoanRecordCheckOutUseCase {
  /**
   * 貸出記録を登録する。
   *
   * @param loanRecordCheckOutInputData 登録する貸出記録の入力データ
   */
  void handle(LoanRecordCheckOutInputData loanRecordCheckOutInputData);
}
