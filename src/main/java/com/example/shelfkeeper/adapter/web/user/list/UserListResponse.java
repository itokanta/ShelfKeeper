package com.example.shelfkeeper.adapter.web.user.list;

import java.util.List;

/**
 * 利用者一覧取得 API のレスポンスボディ。
 *
 * @author itokanta
 */
public class UserListResponse {
  /** 利用者一覧。 */
  private final List<UserListItem> userList;

  public UserListResponse(List<UserListItem> userList) {
    this.userList = userList;
  }

  public List<UserListItem> getUserList() {
    return userList;
  }
}
