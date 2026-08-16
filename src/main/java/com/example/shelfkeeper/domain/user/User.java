package com.example.shelfkeeper.domain.user;

import java.time.LocalDate;

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
  /** 作成日。 */
  private final LocalDate createdAt;
  /** 更新日。 */
  private final LocalDate updatedAt;

	public User(Integer id, String name, LocalDate createdAt, LocalDate updatedAt) {
		this.id = id;
		this.name = name;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}

	public Integer getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public LocalDate getCreatedAt() {
		return createdAt;
	}

	public LocalDate getUpdatedAt() {
		return updatedAt;
	}	
}
