package com.example.shelfkeeper.usecase.loanrecord.bookhistory;

import java.util.List;

/**
 * 書籍の貸出履歴取得ユースケースの出力データ。
 *
 * @author itokanta
 */
public class LoanRecordBookHistoryOutputData {
  /** 貸出履歴一覧。 */
  private final List<LoanRecordBookHistoryItemOutputData> outputData;

  public LoanRecordBookHistoryOutputData(List<LoanRecordBookHistoryItemOutputData> outputData) {
    this.outputData = outputData;
  }

  public List<LoanRecordBookHistoryItemOutputData> getOutputData() {
    return outputData;
  }
}
