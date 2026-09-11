package com.example.shelfkeeper.infrastructure.adminuser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.example.shelfkeeper.domain.adminuser.AdminUser;

/**
 * {@link AdminUserJdbcRepository} のテストクラス。
 *
 * @author itokanta
 */
@SpringBootTest
@Transactional
public class AdminUserJdbcRepositoryTest {
  private final AdminUserJdbcRepository adminUserJdbcRepository;

  @Autowired
  public AdminUserJdbcRepositoryTest(AdminUserJdbcRepository adminUserJdbcRepository) {
    this.adminUserJdbcRepository = adminUserJdbcRepository;
  }

  /**
   * 管理者ユーザーを登録し、メールアドレスで取得できることを検証する。
   */
  @Test
  void adminUserCreateThenFindByMail() {
    adminUserJdbcRepository.adminUserCreate(new AdminUser(null, "test", "test@test.com", "testHashed"));

    AdminUser found = adminUserJdbcRepository.findByMail("test@test.com").orElseThrow();
    assertEquals("test", found.getName());
    assertEquals("test@test.com", found.getMail());
    assertEquals("testHashed", found.getPass());
  }

  /**
   * 管理者ユーザーを更新し、識別子で取得できることを検証する。
   */
  @Test
  void adminUserUpdateThenFindById() {
    adminUserJdbcRepository.adminUserCreate(new AdminUser(null, "test", "test@test.com", "testHashed"));
    AdminUser created = adminUserJdbcRepository.findByMail("test@test.com").orElseThrow();

    adminUserJdbcRepository.adminUserUpdate(new AdminUser(created.getId(), "test2", "test2@test.com", "testHashed2"));

    AdminUser updated = adminUserJdbcRepository.findById(created.getId());
    assertEquals("test2", updated.getName());
    assertEquals("test2@test.com", updated.getMail());
    assertEquals("testHashed2", updated.getPass());
  }

  /**
   * 管理者ユーザーを削除し、メールアドレスで取得できなくなることを検証する。
   */
  @Test
  void adminUserDeleteThenFIndByMail() {
    adminUserJdbcRepository.adminUserCreate(new AdminUser(null, "test", "test@test.com", "testHashed"));
    AdminUser created = adminUserJdbcRepository.findByMail("test@test.com").orElseThrow();

    adminUserJdbcRepository.adminUserDelete(created.getId());

    assertTrue(adminUserJdbcRepository.findByMail("test@test.com").isEmpty());
  }
}
