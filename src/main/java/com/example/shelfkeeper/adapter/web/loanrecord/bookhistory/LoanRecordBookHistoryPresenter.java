package com.example.shelfkeeper.adapter.web.loanrecord.bookhistory;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import com.example.shelfkeeper.usecase.loanrecord.bookhistory.LoanRecordBookHistoryBoundary;
import com.example.shelfkeeper.usecase.loanrecord.bookhistory.LoanRecordBookHistoryOutputData;

/**
 * {@link LoanRecordBookHistoryBoundary} の実装クラス。
 * ユースケースの出力データを API レスポンスへ変換する。
 *
 * @author itokanta
 */
@Component
@RequestScope
public class LoanRecordBookHistoryPresenter implements LoanRecordBookHistoryBoundary {
  /** API レスポンス。 */
  private LoanRecordBookHistoryResponse response;

  /**
   * 取得結果をレスポンスへ変換する。
   *
   * @param loanRecordBookHistoryOutputData 取得した貸出履歴の出力データ
   */
  @Override
  public void complete(LoanRecordBookHistoryOutputData loanRecordBookHistoryOutputData) {
    
  }

  public LoanRecordBookHistoryResponse getResponse() {
    return response;
  }
}
