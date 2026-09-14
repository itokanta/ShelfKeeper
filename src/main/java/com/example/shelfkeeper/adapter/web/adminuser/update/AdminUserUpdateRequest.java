package com.example.shelfkeeper.adapter.web.adminuser.update;

import com.example.shelfkeeper.usecase.adminuser.update.AdminUserUpdateInputData;

import jakarta.validation.constraints.Email;

/**
 * 管理者ユーザー更新 API のリクエストボディ。
 *
 * @author itokanta
 */
public class AdminUserUpdateRequest {
  /** 管理者の名前。更新しない場合は {@code null}。 */
  private String name;

  /** メールアドレス。更新しない場合は {@code null}。 */
  @Email(message = "メールアドレスとして不正な形式です")
  private String mail;

  /** パスワード。更新しない場合は {@code null}。 */
  private String pass;

  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }
  public String getMail() {
    return mail;
  }
  public void setMail(String mail) {
    this.mail = mail;
  }
  public String getPass() {
    return pass;
  }
  public void setPass(String pass) {
    this.pass = pass;
  }

  /**
   * ユースケース入力データへ変換する。
   *
   * @param id 更新対象の管理者ユーザーの識別子
   * @return 管理者ユーザー更新の入力データ
   */
  public AdminUserUpdateInputData toInputData(Integer id) {
    return new AdminUserUpdateInputData(id, name, mail, pass);
  }
}
