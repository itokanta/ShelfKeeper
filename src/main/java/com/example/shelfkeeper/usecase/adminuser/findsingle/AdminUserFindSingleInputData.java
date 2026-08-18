package com.example.shelfkeeper.usecase.adminuser.findsingle;

/**
 * 管理者ユーザー1件取得ユースケースの入力データ。
 *
 * @author itokanta
 */
public class AdminUserFindSingleInputData {
  /** 取得対象の管理者ユーザーの識別子。 */
  private final Integer id;

  public AdminUserFindSingleInputData(Integer id) {
    this.id = id;
  }

  public Integer getId() {
    return id;
  }
}