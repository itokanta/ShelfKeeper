package com.example.shelfkeeper.usecase.adminuser.findsingle;

import java.time.LocalDate;

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
  /** 作成日。 */
  private final LocalDate createdAt;
  /** 更新日。 */
  private final LocalDate updatedAt;

  public AdminUserFindSingleOutputData(String name, String mail, LocalDate createdAt, LocalDate updatedAt) {
    this.name = name;
    this.mail = mail;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
  }

  public String getName() {
    return name;
  }

  public String getMail() {
    return mail;
  }

  public LocalDate getCreatedAt() {
    return createdAt;
  }

  public LocalDate getUpdatedAt() {
    return updatedAt;
  }
}
