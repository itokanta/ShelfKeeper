package com.example.shelfkeeper.usecase.book.create;

/**
 * 蔵書作成ユースケースの入力データ。
 *
 * @author itokanta
 */
public class BookCreateInputData {
  /** タイトル。 */
  private final String title;
  /** 著者名。 */
  private final String authorName;

  public BookCreateInputData(String title, String authorName) {
    this.title = title;
    this.authorName = authorName;
  }

  public String getTitle() {
    return title;
  }

  public String getAuthorName() {
    return authorName;
  }
}
