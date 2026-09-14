package com.example.shelfkeeper.security;

/**
 * ログインリクエストのボディ。
 *
 * @author itokanta
 */
public class LoginRequest {
  /** メールアドレス。 */
  private String mail;
  /** パスワード。 */
  private String pass;

  public String getMail() {
    return mail;
  }
  public String getPass() {
    return pass;
  }

  public void setMail(String mail) {
    this.mail = mail;
  }
  public void setPass(String pass) {
    this.pass = pass;
  }
}
