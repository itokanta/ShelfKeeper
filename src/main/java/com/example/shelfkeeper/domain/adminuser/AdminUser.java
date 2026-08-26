package com.example.shelfkeeper.domain.adminuser;

/**
 * 管理者ユーザーを表すドメインクラス。
 *
 * @author itokanta
 */
public class AdminUser {
  /** 管理者ユーザーの識別子。 */
  private final Integer id;
  /** 管理者の名前。 */
  private final String name;
  /** メールアドレス。 */
  private final String mail;
  /** パスワード。 */
  private final String pass;

  public AdminUser(Integer id, String name, String mail, String pass) {
    this.id = id;
    this.name = name;
    this.mail = mail;
    this.pass = pass;
  }

  public Integer getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getMail() {
    return mail;
  }

  public String getPass() {
    return pass;
  }
}
