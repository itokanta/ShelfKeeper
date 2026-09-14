package com.example.shelfkeeper.adapter.web.loanrecord.bookhistory;

import java.util.List;

/**
 * 書籍の貸出履歴取得 API のレスポンスボディ。
 *
 * @author itokanta
 */
public class LoanRecordBookHistoryResponse {
  /** 貸出履歴一覧。 */
  private final List<LoanRecordBookHistoryItem> loanRecordBookHistoryList;

  public LoanRecordBookHistoryResponse(List<LoanRecordBookHistoryItem> loanRecordBookHistoryList) {
    this.loanRecordBookHistoryList = loanRecordBookHistoryList;
  }

  public List<LoanRecordBookHistoryItem> getLoanRecordBookHistoryList() {
    return loanRecordBookHistoryList;
  }
}
