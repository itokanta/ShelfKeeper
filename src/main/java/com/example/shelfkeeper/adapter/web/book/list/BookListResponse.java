package com.example.shelfkeeper.adapter.web.book.list;

import java.util.List;

/**
 * 蔵書一覧取得 API のレスポンスボディ。
 *
 * @author itokanta
 */
public class BookListResponse {
  /** 蔵書一覧。 */
  private final List<BookListItem> bookList;

  public BookListResponse(List<BookListItem> bookList) {
    this.bookList = bookList;
  }

  public List<BookListItem> getBookList() {
    return bookList;
  }
}
