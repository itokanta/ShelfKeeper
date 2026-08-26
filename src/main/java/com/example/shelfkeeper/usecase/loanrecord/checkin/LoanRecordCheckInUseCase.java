package com.example.shelfkeeper.usecase.loanrecord.checkin;

/**
 * 貸出記録を返却するユースケース。
 *
 * @author itokanta
 */
public interface LoanRecordCheckInUseCase {
  /**
   * 貸出記録を返却する。
   *
   * @param loanRecordCheckInInputData 返却する貸出記録の入力データ
   */
  void handle(LoanRecordCheckInInputData loanRecordCheckInInputData);
}
