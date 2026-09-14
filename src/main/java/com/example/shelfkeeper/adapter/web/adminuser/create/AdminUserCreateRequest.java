package com.example.shelfkeeper.adapter.web.adminuser.create;

import com.example.shelfkeeper.usecase.adminuser.create.AdminUserCreateInputData;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

/**
 * 管理者ユーザー作成 API のリクエストボディ。
 *
 * @author itokanta
 */
public class AdminUserCreateRequest {
  /** 管理者の名前。 */
  @NotBlank(message = "名前は必須です")
  private String name;

  /** メールアドレス。 */
  @NotBlank(message = "メールアドレスは必須です")
  @Email(message = "メールアドレスとして不正な形式です")
  private String mail;

  /** パスワード。 */
  @NotBlank(message = "パスワードは必須です")
  private String pass;

  public String getName() {
    return name;
  }

  public String getMail() {
    return mail;
  }

  public String getPass() {
    return pass;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setMail(String mail) {
    this.mail = mail;
  }

  public void setPass(String pass) {
    this.pass = pass;
  }

  /**
   * ユースケース入力データへ変換する。
   *
   * @return 管理者ユーザー作成の入力データ
   */
  public AdminUserCreateInputData toInputData() {
    return new AdminUserCreateInputData(name, mail, pass);
  }
}
