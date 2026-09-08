package com.example.shelfkeeper.adapter.web.user.overduelist;

import java.util.List;

/**
 * 延滞利用者一覧取得 API のレスポンスボディ。
 *
 * @author itokanta
 */
public class UserOverDueListResponse {
  /** 延滞利用者一覧。 */
  private final List<UserOverDueListItem> userOverDueList;

  public UserOverDueListResponse(List<UserOverDueListItem> response) {
    this.userOverDueList = response;
  }

  public List<UserOverDueListItem> getUserOverDueList() {
    return userOverDueList;
  }
}
