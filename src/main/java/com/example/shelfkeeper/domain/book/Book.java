package com.example.shelfkeeper.domain.book;

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

	public Book(Integer id, String title, String authorName) {
		this.id = id;
		this.title = title;
		this.authorName = authorName;
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
}
