package com.example.shelfkeeper.domain.book;

import java.time.LocalDate;

/**
 * 蔵書を表すドメインクラス。
 *
 * @author itokanta
 */
public class Book {
  /** 書籍の識別子。 */
  private final Integer id;
  /** タイトル。 */
  private final String title;
  /** 著者名。 */
  private final String authorName;
  /** 作成日。 */
  private final LocalDate createdAt;
  /** 更新日。 */
  private final LocalDate updatedAt;

	public Book(Integer id, String title, String authorName, LocalDate createdAt, LocalDate updatedAt) {
		this.id = id;
		this.title = title;
		this.authorName = authorName;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
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

  public LocalDate getCreatedAt() {
    return createdAt;
  }

  public LocalDate getUpdatedAt() {
    return updatedAt;
  }
}
