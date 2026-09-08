package com.example.shelfkeeper.adapter.web.user.overduelist;

import java.util.List;

/**
 * 延滞利用者一覧の1件分のレスポンス項目。
 *
 * @author itokanta
 */
public class UserOverDueListItem {
  /** 利用者の識別子。 */
  private final Integer userId;
  /** 利用者名。 */
  private final String userName;
  /** 延滞中の書籍一覧。 */
  private final List<DueBookListItem> dueBookList;

  public UserOverDueListItem(Integer userId, String userName, List<DueBookListItem> dueBookList) {
    this.userId = userId;
    this.userName = userName;
    this.dueBookList = dueBookList;
  }

  public Integer getUserId() {
    return userId;
  }

  public String getUserName() {
    return userName;
  }

  public List<DueBookListItem> getDueBookList() {
    return dueBookList;
  }
}
