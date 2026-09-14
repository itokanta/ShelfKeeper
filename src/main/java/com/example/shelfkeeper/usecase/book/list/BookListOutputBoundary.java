package com.example.shelfkeeper.usecase.book.list;

/**
 * 蔵書一覧取得ユースケースの出力境界。
 *
 * @author itokanta
 */
public interface BookListOutputBoundary {
  /**
   * 取得結果を後続処理へ引き渡す。
   *
   * @param bookListOutputData 取得した蔵書一覧の出力データ
   */
  void complete(BookListOutputData bookListOutputData);
}
