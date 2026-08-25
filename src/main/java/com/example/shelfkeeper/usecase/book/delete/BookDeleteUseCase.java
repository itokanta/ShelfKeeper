package com.example.shelfkeeper.usecase.book.delete;

/**
 * 蔵書を削除するユースケース。
 *
 * @author itokanta
 */
public interface BookDeleteUseCase {
  /**
   * 蔵書を削除する。
   *
   * @param bookDeleteInputData 削除する蔵書の入力データ
   */
  void handle(BookDeleteInputData bookDeleteInputData);
}
