package com.example.shelfkeeper.domain.user;

/**
 * 利用者を表すドメインクラス。
 *
 * @author itokanta
 */
public class User {
  /** 利用者の識別子。 */
  private final Integer id;
  /** 利用者名。 */
  private final String name;

	public User(Integer id, String name) {
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
