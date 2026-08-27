package com.example.shelfkeeper.usecase.loanrecord.bookhistory;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.shelfkeeper.infrastructure.entity.loanrecord.BookHistory;
import com.example.shelfkeeper.usecase.port.LoanRecordRepository;

/**
 * {@link LoanRecordBookHistoryUseCase} の実装クラス。
 * 指定された書籍の貸出履歴を取得し、出力境界へ引き渡す。
 *
 * @author itokanta
 */
@Service
public class LoanRecordBookHistoryInteractor implements LoanRecordBookHistoryUseCase {
  /** 貸出記録の永続化を担うリポジトリ。 */
  private final LoanRecordRepository loanRecordRepository;
  /** 取得結果を後続処理へ引き渡す出力境界。 */
  private final LoanRecordBookHistoryBoundary loanRecordBookHistoryBoundary;

  public LoanRecordBookHistoryInteractor(LoanRecordRepository loanRecordRepository,
      LoanRecordBookHistoryBoundary loanRecordBookHistoryBoundary) {
    this.loanRecordRepository = loanRecordRepository;
    this.loanRecordBookHistoryBoundary = loanRecordBookHistoryBoundary;
  }

  /**
   * 書籍の貸出履歴を取得する。
   *
   * @param loanRecordBookHistoryInputData 取得する書籍の入力データ
   */
  @Override
  public void handle(LoanRecordBookHistoryInputData loanRecordBookHistoryInputData) {
    List<BookHistory> findByBookIdResult = loanRecordRepository.findByBookId(loanRecordBookHistoryInputData.getBookId());
    List<LoanRecordBookHistoryItemOutputData> outputItemList = new ArrayList<>();

    for(BookHistory bookHistory : findByBookIdResult) {
      outputItemList.add(new LoanRecordBookHistoryItemOutputData(
        bookHistory.getLoanRecordId(),
        bookHistory.getUserName(),
        bookHistory.getBookName(),
        bookHistory.getLoanDate(),
        bookHistory.getDueDate(),
        bookHistory.getReturnDate()
      ));
    }

    loanRecordBookHistoryBoundary.complete(new LoanRecordBookHistoryOutputData(outputItemList));
  }
}
