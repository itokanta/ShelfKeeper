package com.example.shelfkeeper.usecase.adminuser.delete;

import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.shelfkeeper.usecase.port.AdminUserRepository;

/**
 * {@link AdminUserDeleteInteractor} のテストクラス。
 *
 * @author itokanta
 */
@ExtendWith(MockitoExtension.class)
public class AdminUserDeleteInteractorTest {

  @Mock
  private AdminUserRepository adminUserRepository;

  @InjectMocks
  private AdminUserDeleteInteractor adminUserDeleteInteractor;

  /**
   * 指定した識別子の管理者ユーザーを削除できることを検証する。
   */
  @Test
  void handleSuccess() {
    AdminUserDeleteInputData deleteTarget = new AdminUserDeleteInputData(1);

    adminUserDeleteInteractor.handle(deleteTarget);

    verify(adminUserRepository).adminUserDelete(deleteTarget.getId());
  }
}