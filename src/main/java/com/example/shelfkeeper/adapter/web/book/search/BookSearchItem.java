package com.example.shelfkeeper.adapter.web.book.search;

/**
 * 蔵書検索の1件分のレスポンス項目。
 *
 * @author itokanta
 */
public class BookSearchItem {
  /** 書籍の識別子。 */
  private final Integer bookId;
  /** タイトル。 */
  private final String title;
  /** 著者名。 */
  private final String authorName;
  /** 貸出状態の表示文言。 */
  private final String status;

  public BookSearchItem(Integer bookId, String title, String authorName, String status) {
    this.bookId = bookId;
    this.title = title;
    this.authorName = authorName;
    this.status = status;
  }

  public Integer getBookId() {
    return bookId;
  }

  public String getTitle() {
    return title;
  }

  public String getAuthorName() {
    return authorName;
  }

  public String getStatus() {
    return status;
  }
}
