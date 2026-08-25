package com.example.shelfkeeper.usecase.book.search;

/**
 * 蔵書をタイトルで検索するユースケース。
 *
 * @author itokanta
 */
public interface BookSearchUseCase {
  /**
   * 蔵書をタイトルで検索する。
   *
   * @param bookSearchInputData 検索する蔵書の入力データ
   */
  void handle(BookSearchInputData bookSearchInputData);
}
