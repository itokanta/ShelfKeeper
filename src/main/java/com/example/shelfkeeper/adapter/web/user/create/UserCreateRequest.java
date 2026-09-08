package com.example.shelfkeeper.adapter.web.user.create;

import com.example.shelfkeeper.usecase.user.create.UserCreateInputData;

import jakarta.validation.constraints.NotBlank;

/**
 * 利用者作成 API のリクエストボディ。
 *
 * @author itokanta
 */
public class UserCreateRequest {
  /** 利用者名。 */
  @NotBlank(message = "名前は必須です")
  private String name;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  /**
   * ユースケース入力データへ変換する。
   *
   * @return 利用者作成の入力データ
   */
  public UserCreateInputData toUserCreateInputData() {
    return new UserCreateInputData(name);
  }
}
