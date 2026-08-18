package com.example.shelfkeeper.usecase.adminuser.update;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.shelfkeeper.domain.adminuser.AdminUser;
import com.example.shelfkeeper.usecase.port.AdminUserRepository;

/**
 * {@link AdminUserUpdateInteractor} のテストクラス。
 *
 * @author itokanta
 */
@ExtendWith(MockitoExtension.class)
public class AdminUserUpdateInteractorTest {

  @Mock
  private AdminUserRepository adminUserRepository;

  @Mock
  private PasswordEncoder passwordEncoder;

  @InjectMocks
  private AdminUserUpdateInteractor adminUserUpdateInteractor;

  /**
   * 名前・メールアドレス・パスワードをすべて更新できることを検証する。
   */
  @Test
  void handleSuccessAllUpdate() {
    AdminUser dbData = new AdminUser(1, "test", "test@test", "hashed-pass", null, null);
    AdminUserUpdateInputData inputData = new AdminUserUpdateInputData(1, "test2", "test2@test2", "test2test2");

    when(adminUserRepository.findById(inputData.getId())).thenReturn(dbData);
    when(passwordEncoder.encode(inputData.getPass())).thenReturn("hashed-pass2");

    adminUserUpdateInteractor.handle(inputData);

    verify(adminUserRepository).findById(inputData.getId());
    ArgumentCaptor<AdminUser> captor = ArgumentCaptor.forClass(AdminUser.class);
    verify(adminUserRepository).adminUserUpdate(captor.capture());

    AdminUser newUser = captor.getValue();
    assertEquals(inputData.getName(), newUser.getName());
    assertEquals(inputData.getMail(), newUser.getMail());
    assertEquals("hashed-pass2", newUser.getPass());
  }

  /**
   * 入力がすべて {@code null} の場合、既存の値を維持して更新することを検証する。
   */
  @Test
  void handleSuccessNoUpdate() {
    AdminUser dbData = new AdminUser(1, "test", "test@test", "hashed-pass", null, null);
    AdminUserUpdateInputData inputData = new AdminUserUpdateInputData(1, null, null, null);

    when(adminUserRepository.findById(inputData.getId())).thenReturn(dbData);

    adminUserUpdateInteractor.handle(inputData);

    verify(adminUserRepository).findById(inputData.getId());
    ArgumentCaptor<AdminUser> captor = ArgumentCaptor.forClass(AdminUser.class);
    verify(adminUserRepository).adminUserUpdate(captor.capture());

    AdminUser newUser = captor.getValue();
    assertEquals("test", newUser.getName());
    assertEquals("test@test", newUser.getMail());
    assertEquals("hashed-pass", newUser.getPass());
  }
}
