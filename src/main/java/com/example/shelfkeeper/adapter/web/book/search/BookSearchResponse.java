package com.example.shelfkeeper.adapter.web.book.search;

import java.util.List;

/**
 * 蔵書検索 API のレスポンスボディ。
 *
 * @author itokanta
 */
public class BookSearchResponse {
  /** 検索結果の蔵書一覧。 */
  private final List<BookSearchItem> searchBookList;

  public BookSearchResponse(List<BookSearchItem> itemList) {
    this.searchBookList = itemList;
  }

  public List<BookSearchItem> getSearchBookList() {
    return searchBookList;
  }
}
