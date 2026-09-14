package com.example.shelfkeeper.usecase.book.delete;

/**
 * 蔵書削除ユースケースの入力データ。
 *
 * @author itokanta
 */
public class BookDeleteInputData {
  /** 削除対象の書籍の識別子。 */
  private final Integer id;

  public BookDeleteInputData(Integer id) {
    this.id = id;
  }

  public Integer getId() {
    return id;
  }
}
