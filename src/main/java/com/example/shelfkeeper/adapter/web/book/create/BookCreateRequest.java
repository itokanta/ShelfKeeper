package com.example.shelfkeeper.adapter.web.book.create;

import com.example.shelfkeeper.usecase.book.create.BookCreateInputData;

import jakarta.validation.constraints.NotBlank;

/**
 * 蔵書作成 API のリクエストボディ。
 *
 * @author itokanta
 */
public class BookCreateRequest {
  /** タイトル。 */
  @NotBlank(message = "タイトルは必須です")
  private String title;
  /** 著者名。 */
  @NotBlank(message = "作者は必須です")
  private String authorName;

  public String getTitle() {
    return title;
  }
  public void setTitle(String title) {
    this.title = title;
  }
  public String getAuthorName() {
    return authorName;
  }
  public void setAuthorName(String authorName) {
    this.authorName = authorName;
  }

  /**
   * ユースケース入力データへ変換する。
   *
   * @return 蔵書作成の入力データ
   */
  public BookCreateInputData toBookCreateInputData() {
    return new BookCreateInputData(title, authorName);
  }
}
