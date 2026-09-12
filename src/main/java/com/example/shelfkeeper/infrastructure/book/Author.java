package com.example.shelfkeeper.infrastructure.book;

/**
 * 著者を表すインフラ層のエンティティ。
 *
 * @author itokanta
 */
public class Author {
  /** 著者の識別子。 */
  private final Integer id;
  /** 著者名。 */
  private final String name;

  public Author(Integer id, String name) {
    this.id = id;
    this.name = name;
  }

  public Integer getId() {
    return id;
  }

  public String getName() {
    return name;
  }
}
