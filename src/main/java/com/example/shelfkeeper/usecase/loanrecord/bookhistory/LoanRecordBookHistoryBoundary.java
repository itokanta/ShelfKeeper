package com.example.shelfkeeper.usecase.loanrecord.bookhistory;

/**
 * 書籍の貸出履歴取得ユースケースの出力境界。
 *
 * @author itokanta
 */
public interface LoanRecordBookHistoryBoundary {
  /**
   * 取得結果を後続処理へ引き渡す。
   *
   * @param loanRecordBookHistoryOutputData 取得した貸出履歴の出力データ
   */
  void complete(LoanRecordBookHistoryOutputData loanRecordBookHistoryOutputData);
}
