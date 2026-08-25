package com.example.shelfkeeper.usecase.book.create;

/**
 * 蔵書を作成するユースケース。
 *
 * @author itokanta
 */
public interface BookCreateUseCase {
  /**
   * 蔵書を作成する。
   *
   * @param bookCreateInputData 作成する蔵書の入力データ
   */
  void handle(BookCreateInputData bookCreateInputData);
}
