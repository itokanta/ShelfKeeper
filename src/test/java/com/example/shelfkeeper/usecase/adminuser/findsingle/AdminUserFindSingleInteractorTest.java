package com.example.shelfkeeper.usecase.adminuser.findsingle;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.shelfkeeper.domain.adminuser.AdminUser;
import com.example.shelfkeeper.usecase.port.AdminUserRepository;

/**
 * {@link AdminUserFindSingleInteractor} のテストクラス。
 *
 * @author itokanta
 */
@ExtendWith(MockitoExtension.class)
public class AdminUserFindSingleInteractorTest {

  @Mock
  private AdminUserRepository adminUserRepository;

  @Mock
  private AdminUserFindSingleOutputBoundary adminUserFindSingleOutputBoundary;

  @InjectMocks
  private AdminUserFindSingleInteractor adminUserFindSingleInteractor;

  /**
   * 指定した識別子の管理者ユーザーを取得し、出力境界へ引き渡すことを検証する。
   */
  @Test
  void handleSuccess() {
    AdminUserFindSingleInputData targetUser = new AdminUserFindSingleInputData(1);
    AdminUser findByIdResult = new AdminUser(1, "test", "test@test", "hashed^pass");

    when(adminUserRepository.findById(targetUser.getId())).thenReturn(findByIdResult);

    adminUserFindSingleInteractor.handle(targetUser);

    verify(adminUserRepository).findById(targetUser.getId());
    ArgumentCaptor<AdminUserFindSingleOutputData> captor = ArgumentCaptor.forClass(AdminUserFindSingleOutputData.class);
    verify(adminUserFindSingleOutputBoundary).complete(captor.capture());

    AdminUserFindSingleOutputData outputData = captor.getValue();
    assertEquals(findByIdResult.getName(), outputData.getName());
    assertEquals(findByIdResult.getMail(), outputData.getMail());
  }
}
