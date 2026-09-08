package com.example.shelfkeeper.adapter.web.user.list;

/**
 * 利用者一覧の1件分のレスポンス項目。
 *
 * @author itokanta
 */
public class UserListItem {
  /** 利用者の識別子。 */
  private final Integer id;
  /** 利用者名。 */
  private final String name;

  public UserListItem(Integer id, String name) {
    this.id = id;
    this.name = name;
  }

  public Integer getId() {
    return id;
  }

  public String getName() {
    return name;
  }
}
