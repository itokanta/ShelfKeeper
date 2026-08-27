package com.example.shelfkeeper.usecase.loanrecord.bookhistory;

/**
 * 書籍の貸出履歴を取得するユースケース。
 *
 * @author itokanta
 */
public interface LoanRecordBookHistoryUseCase {
  /**
   * 書籍の貸出履歴を取得する。
   *
   * @param loanRecordBookHistoryInputData 取得する書籍の入力データ
   */
  void handle(LoanRecordBookHistoryInputData loanRecordBookHistoryInputData);
}
