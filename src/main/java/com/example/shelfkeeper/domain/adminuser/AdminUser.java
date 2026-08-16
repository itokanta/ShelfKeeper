package com.example.shelfkeeper.domain.adminuser;

import java.time.LocalDate;

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
  /** 作成日。 */
  private final LocalDate createdAt;
  /** 更新日。 */
  private final LocalDate updatedAt;

  public AdminUser(Integer id, String name, String mail, String pass, LocalDate createdAt, LocalDate updatedAt) {
    this.id = id;
    this.name = name;
    this.mail = mail;
    this.pass = pass;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
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

  public LocalDate getCreatedAt() {
    return createdAt;
  }

  public LocalDate getUpdatedAt() {
    return updatedAt;
  }
}
