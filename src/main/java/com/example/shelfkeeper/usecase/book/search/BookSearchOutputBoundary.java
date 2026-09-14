package com.example.shelfkeeper.usecase.book.search;

/**
 * 蔵書検索ユースケースの出力境界。
 *
 * @author itokanta
 */
public interface BookSearchOutputBoundary {
  /**
   * 検索結果を後続処理へ引き渡す。
   *
   * @param bookSearchOutputData 検索した蔵書一覧の出力データ
   */
  void complete(BookSearchOutputData bookSearchOutputData);
}
