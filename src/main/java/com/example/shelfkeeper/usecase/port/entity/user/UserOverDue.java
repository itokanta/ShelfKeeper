package com.example.shelfkeeper.usecase.port.entity.user;

import java.util.List;

import com.example.shelfkeeper.usecase.port.entity.book.UserOverDueBook;

/**
 * 延滞利用者とその延滞書籍一覧を表すエンティティ。
 *
 * @author itokanta
 */
public class UserOverDue {
  /** 利用者の識別子。 */
  private final Integer userId;
  /** 利用者名。 */
  private final String userName;
  /** 延滞中の書籍一覧。 */
  private final List<UserOverDueBook> dueBookList;
  
  public UserOverDue(Integer userId, String userName, List<UserOverDueBook> dueBookList) {
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

  public List<UserOverDueBook> getDueBookList() {
    return dueBookList;
  }
}
