package com.example.shelfkeeper.usecase.adminuser.findsingle;

import com.example.shelfkeeper.domain.adminuser.AdminUser;
import com.example.shelfkeeper.usecase.port.AdminUserRepository;

/**
 * {@link AdminUserFindSingleUseCase} の実装クラス。
 * 指定された識別子の管理者ユーザーを取得し、出力境界へ引き渡す。
 *
 * @author itokanta
 */
public class AdminUserFindSingleInteractor implements AdminUserFindSingleUseCase{
  /** 管理者ユーザーの永続化を担うリポジトリ。 */
  private final AdminUserRepository adminUserRepository;
  /** 取得結果を後続処理へ引き渡す出力境界。 */
  private final AdminUserFindSingleOutputBoundary adminUserFindSingleOutputBoundary;

  public AdminUserFindSingleInteractor(AdminUserRepository adminUserRepository,
      AdminUserFindSingleOutputBoundary adminUserFindSingleOutputBoundary) {
    this.adminUserRepository = adminUserRepository;
    this.adminUserFindSingleOutputBoundary = adminUserFindSingleOutputBoundary;
  }

  /**
   * 管理者ユーザーを1件取得する。
   *
   * @param adminUserFindSingleInputData 取得する管理者ユーザーの入力データ
   */
  @Override
  public void handle(AdminUserFindSingleInputData adminUserFindSingleInputData) {
    AdminUser adminUser = adminUserRepository.findById(adminUserFindSingleInputData.getId());

    AdminUserFindSingleOutputData outputData = new AdminUserFindSingleOutputData(adminUser.getName(), adminUser.getMail(), adminUser.getCreatedAt(), adminUser.getUpdatedAt());

    adminUserFindSingleOutputBoundary.complete(outputData);
  }
}
