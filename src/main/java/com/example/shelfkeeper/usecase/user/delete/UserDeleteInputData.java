package com.example.shelfkeeper.usecase.user.delete;

/**
 * 利用者削除ユースケースの入力データ。
 *
 * @author itokanta
 */
public class UserDeleteInputData {
  /** 削除対象の利用者の識別子。 */
  private final Integer id;

  public UserDeleteInputData(Integer id) {
    this.id = id;
  }

  public Integer getId() {
    return id;
  }
}
