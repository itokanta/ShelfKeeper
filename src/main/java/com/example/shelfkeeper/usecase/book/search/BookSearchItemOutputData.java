package com.example.shelfkeeper.usecase.book.search;

/**
 * 蔵書検索結果の1件分の出力データ。
 *
 * @author itokanta
 */
public class BookSearchItemOutputData {
  /** 書籍の識別子。 */
  private final Integer bookId;
  /** タイトル。 */
  private final String title;
  /** 著者名。 */
  private final String authorName;
  /** 貸出状態。 */
  private final Boolean status;

  public BookSearchItemOutputData(Integer bookId, String title, String authorName, Boolean status) {
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

  public Boolean getStatus() {
    return status;
  }
}
