package com.example.shelfkeeper.usecase.book.list;

/**
 * 蔵書一覧の1件分の出力データ。
 *
 * @author itokanta
 */
public class BookListItemOutputData {
  /** タイトル。 */
  private final String title;
  /** 著者名。 */
  private final String authorName;
  /** 貸出状態。 */
  private final Boolean status;

  public BookListItemOutputData(String title, String authorName, Boolean status) {
    this.title = title;
    this.authorName = authorName;
    this.status = status;
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
