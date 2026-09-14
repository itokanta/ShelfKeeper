package com.example.shelfkeeper.adapter.web.user.update;

import com.example.shelfkeeper.usecase.user.update.UserUpdateInputData;

/**
 * 利用者更新 API のリクエストボディ。
 *
 * @author itokanta
 */
public class UserUpdateRequest {
  /** 利用者名。更新しない場合は {@code null}。 */
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
   * @param id 更新対象の利用者の識別子
   * @return 利用者更新の入力データ
   */
  public UserUpdateInputData toUserUpdateInputData(Integer id) {
    return new UserUpdateInputData(id, name);
  }
}
