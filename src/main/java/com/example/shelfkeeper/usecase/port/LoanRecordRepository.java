package com.example.shelfkeeper.usecase.port;

import java.util.List;
import java.util.Optional;

import com.example.shelfkeeper.domain.loanrecord.LoanRecord;
import com.example.shelfkeeper.usecase.port.entity.loanrecord.BookHistory;

/**
 * 貸出記録の永続化を担うリポジトリ。
 *
 * @author itokanta
 */
public interface LoanRecordRepository {
  /**
   * 識別子で貸出記録を取得する。
   *
   * @param id 貸出記録の識別子
   * @return 該当する貸出記録。存在しない場合は空
   */
  Optional<LoanRecord> findById(Integer id);

  /**
   * 書籍の識別子で未返却の貸出記録を取得する。
   *
   * @param bookId 書籍の識別子
   * @return 未返却の貸出記録。存在しない場合は空
   */
  Optional<LoanRecord> returnDateIsNullFindByBookId(Integer bookId);

  /**
   * 貸出を登録する。
   *
   * @param loanRecord 登録する貸出記録
   */
  void checkOut(LoanRecord loanRecord);

  /**
   * 貸出記録を返却する。
   *
   * @param loanRecord 返却する貸出記録
   */
  void checkIn(LoanRecord loanRecord);

  /**
   * 書籍の識別子で貸出履歴を取得する。
   *
   * @param bookId 書籍の識別子
   * @return 該当する貸出履歴の一覧
   */
  List<BookHistory> findByBookId(Integer bookId);
}
