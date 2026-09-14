package com.example.shelfkeeper.usecase.adminuser.update;

/**
 * 管理者ユーザー更新ユースケースの入力データ。
 *
 * @author itokanta
 */
public class AdminUserUpdateInputData {
  /** 更新対象の管理者ユーザーの識別子。 */
  private final Integer id;
  /** 管理者の名前。更新しない場合は {@code null}。 */
  private final String name;
  /** メールアドレス。更新しない場合は {@code null}。 */
  private final String mail;
  /** パスワード。更新しない場合は {@code null}。 */
  private final String pass;

  public AdminUserUpdateInputData(Integer id, String name, String mail, String pass) {
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