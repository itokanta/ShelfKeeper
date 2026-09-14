package com.example.shelfkeeper.usecase.user.list;

import java.util.List;

/**
 * 利用者一覧取得ユースケースの出力データ。
 *
 * @author itokanta
 */
public class UserListOutputData {
  /** 利用者一覧。 */
  private final List<UserListItemOutputData> userList;

  public UserListOutputData(List<UserListItemOutputData> userList) {
    this.userList = userList;
  }

  public List<UserListItemOutputData> getUserList() {
    return userList;
  }
}
