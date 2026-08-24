package com.example.shelfkeeper.usecase.user.overduelist;

import java.util.List;

/**
 * 延滞利用者一覧の1件分の出力データ。
 *
 * @author itokanta
 */
public class UserOverDueListItemOutputData {
  /** 利用者の識別子。 */
  private final Integer userId;
  /** 利用者名。 */
  private final String userName;
  /** 延滞中の書籍一覧。 */
  private final List<UserOverDueListDueBookItem> dueBookList;

  public UserOverDueListItemOutputData(Integer userId, String userName, List<UserOverDueListDueBookItem> dueBookList) {
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

  public List<UserOverDueListDueBookItem> getDueBookList() {
    return dueBookList;
  }
}
