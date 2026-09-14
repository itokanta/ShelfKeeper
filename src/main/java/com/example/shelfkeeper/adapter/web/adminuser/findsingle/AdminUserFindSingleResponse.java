package com.example.shelfkeeper.adapter.web.adminuser.findsingle;

/**
 * 管理者ユーザー1件取得 API のレスポンスボディ。
 *
 * @author itokanta
 */
public class AdminUserFindSingleResponse {
  /** 管理者の名前。 */
  private final String name;
  /** メールアドレス。 */
  private final String mail;

  public AdminUserFindSingleResponse(String name, String mail) {
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
