package com.example.shelfkeeper.usecase.port.entity.book;

/**
 * 蔵書とその貸出状態を表すエンティティ。
 *
 * @author itokanta
 */
public class BookStatus {
  /** 書籍の識別子。 */
  private final Integer id;
  /** タイトル。 */
  private final String title;
  /** 著者名。 */
  private final String authorName;
  /** 貸出状態。 */
  private final Boolean status;

  public BookStatus(Integer id, String title, String authorName, Boolean status) {
    this.id = id;
    this.title = title;
    this.authorName = authorName;
    this.status = status;
  }

  public Integer getId() {
    return id;
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
