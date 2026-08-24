package com.example.shelfkeeper.usecase.user.list;

/**
 * 利用者一覧の1件分の出力データ。
 *
 * @author itokanta
 */
public class UserListItemOutputData {
  /** 利用者の識別子。 */
  private Integer id;
  /** 利用者名。 */
  private String name;

  public UserListItemOutputData(Integer id, String name) {
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
