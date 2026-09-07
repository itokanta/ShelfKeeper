package com.example.shelfkeeper.adapter.web.book.search;

import com.example.shelfkeeper.usecase.book.search.BookSearchInputData;

/**
 * 蔵書検索 API のリクエストパラメータ。
 *
 * @author itokanta
 */
public class BookSearchRequest {
  /** 検索するタイトル。 */
  private String title;

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  /**
   * ユースケース入力データへ変換する。
   *
   * @return 蔵書検索の入力データ
   */
  public BookSearchInputData toBookSearchInputData() {
    return new BookSearchInputData(title);
  }
}
