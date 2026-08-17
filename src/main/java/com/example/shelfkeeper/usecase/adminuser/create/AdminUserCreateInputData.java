package com.example.shelfkeeper.usecase.adminuser.create;

/**
 * 管理者ユーザー作成ユースケースの入力データ。
 *
 * @author itokanta
 */
public class AdminUserCreateInputData {
  /** 管理者の名前。 */
  private final String name;
  /** メールアドレス。 */
  private final String mail;
  /** パスワード。 */
  private final String pass;
  
  public AdminUserCreateInputData(String name, String mail, String pass) {
    this.name = name;
    this.mail = mail;
    this.pass = pass;
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
