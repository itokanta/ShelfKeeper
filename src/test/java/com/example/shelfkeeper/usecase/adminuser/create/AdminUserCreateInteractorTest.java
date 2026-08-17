package com.example.shelfkeeper.usecase.adminuser.create;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

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
 * {@link AdminUserCreateInteractor} のテストクラス。
 *
 * @author itokanta
 */
@ExtendWith(MockitoExtension.class)
public class AdminUserCreateInteractorTest {

  @Mock
  private AdminUserRepository adminUserRepository;

  @Mock
  private PasswordEncoder passwordEncoder;

  @InjectMocks
  private AdminUserCreateInteractor adminUserCreateInteractor;

  /**
   * 未登録のメールアドレスで管理者ユーザーを作成できることを検証する。
   */
  @Test
  void handleSuccess() {
    AdminUserCreateInputData newUser = new AdminUserCreateInputData("test", "test@test", "testtest");

    when(adminUserRepository.findByMail(newUser.getMail())).thenReturn(Optional.empty());
    when(passwordEncoder.encode(newUser.getPass())).thenReturn("hashed-pass");

    adminUserCreateInteractor.handle(newUser);

    verify(adminUserRepository).findByMail(newUser.getMail());
    ArgumentCaptor<AdminUser> captor = ArgumentCaptor.forClass(AdminUser.class);
    verify(adminUserRepository).adminUserCreate(captor.capture());

    AdminUser created = captor.getValue();
    assertEquals("test", created.getName());
    assertEquals("test@test", created.getMail());
    assertEquals("hashed-pass", created.getPass());
  }

  /**
   * 既に登録済みのメールアドレスの場合、例外をスローし登録しないことを検証する。
   */
  @Test
  void handleError() {
    AdminUserCreateInputData newUser = new AdminUserCreateInputData("test", "test@test", "testtest");

    when(adminUserRepository.findByMail(newUser.getMail())).thenReturn(Optional.of(new AdminUser(1, "test", "test@test", "hashed-pass", null, null)));

    assertThrows(RuntimeException.class, () -> adminUserCreateInteractor.handle(newUser));

    verify(adminUserRepository).findByMail(newUser.getMail());
    verify(adminUserRepository, never()).adminUserCreate(any());
  }
}
