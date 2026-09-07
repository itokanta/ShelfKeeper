package com.example.shelfkeeper.adapter.web.book.search;

import com.example.shelfkeeper.usecase.book.search.BookSearchInputData;

import jakarta.validation.constraints.NotBlank;

/**
 * 蔵書検索 API のリクエストパラメータ。
 *
 * @author itokanta
 */
public class BookSearchRequest {
  /** 検索するタイトル。 */
  @NotBlank(message = "値は必須です")
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
