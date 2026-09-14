package com.example.shelfkeeper.usecase.book.search;

/**
 * 蔵書検索ユースケースの入力データ。
 *
 * @author itokanta
 */
public class BookSearchInputData {
  /** 検索する書籍のタイトル。 */
  private final String title;

  public BookSearchInputData(String title) {
    this.title = title;
  }

  public String getTitle() {
    return title;
  }
}
