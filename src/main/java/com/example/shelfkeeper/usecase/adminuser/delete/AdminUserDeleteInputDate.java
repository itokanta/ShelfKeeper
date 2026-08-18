package com.example.shelfkeeper.usecase.adminuser.delete;

/**
 * 管理者ユーザー削除ユースケースの入力データ。
 *
 * @author itokanta
 */
public class AdminUserDeleteInputDate {
  /** 削除対象の管理者ユーザーの識別子。 */
  private final Integer id;

  public AdminUserDeleteInputDate(Integer id) {
    this.id = id;
  }

  public Integer getId() {
    return id;
  }
}
