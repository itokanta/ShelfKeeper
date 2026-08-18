package com.example.shelfkeeper.usecase.adminuser.delete;

import org.springframework.stereotype.Service;

import com.example.shelfkeeper.usecase.port.AdminUserRepository;

/**
 * {@link AdminUserDeleteUseCase} の実装クラス。
 * 指定された識別子の管理者ユーザーを削除する。
 *
 * @author itokanta
 */
@Service
public class AdminUserDeleteInteractor implements AdminUserDeleteUseCase{
  /** 管理者ユーザーの永続化を担うリポジトリ。 */
  private final AdminUserRepository adminUserRepository;

  public AdminUserDeleteInteractor(AdminUserRepository adminUserRepository) {
    this.adminUserRepository = adminUserRepository;
  }

  /**
   * 管理者ユーザーを削除する。
   *
   * @param adminUserDeleteInputData 削除する管理者ユーザーの入力データ
   */
  @Override
  public void handle(AdminUserDeleteInputData adminUserDeleteInputData) {
    adminUserRepository.adminUserDelete(adminUserDeleteInputData.getId());
  }
}
