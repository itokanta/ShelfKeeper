package com.example.shelfkeeper.usecase.user.create;

/**
 * 利用者作成ユースケースの入力データ。
 *
 * @author itokanta
 */
public class UserCreateInputData {
  /** 利用者名。 */
  private final String name;

  public UserCreateInputData(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }
}
