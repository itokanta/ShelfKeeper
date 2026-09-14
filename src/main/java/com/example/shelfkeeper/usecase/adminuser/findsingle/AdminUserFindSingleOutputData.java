package com.example.shelfkeeper.usecase.adminuser.findsingle;

/**
 * 管理者ユーザー1件取得ユースケースの出力データ。
 *
 * @author itokanta
 */
public class AdminUserFindSingleOutputData {
  /** 管理者の名前。 */
  private final String name;
  /** メールアドレス。 */
  private final String mail;

  public AdminUserFindSingleOutputData(String name, String mail) {
    this.name = name;
    this.mail = mail;
  }

  public String getName() {
    return name;
  }

  public String getMail() {
    return mail;
  }
}
