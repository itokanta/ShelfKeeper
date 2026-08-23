package com.example.shelfkeeper.usecase.user.update;

/**
 * 利用者更新ユースケースの入力データ。
 *
 * @author itokanta
 */
public class UserUpdateInputData {
  /** 更新対象の利用者の識別子。 */
  private final Integer id;
  /** 利用者名。更新しない場合は {@code null}。 */
  private final String name;

  public UserUpdateInputData(Integer id, String name) {
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
