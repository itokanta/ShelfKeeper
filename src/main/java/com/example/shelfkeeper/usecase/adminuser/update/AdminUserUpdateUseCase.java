package com.example.shelfkeeper.usecase.adminuser.update;

/**
 * 管理者ユーザーを更新するユースケース。
 *
 * @author itokanta
 */
public interface AdminUserUpdateUseCase {
  /**
   * 管理者ユーザーを更新する。
   *
   * @param adminUserUpdateInputData 更新する管理者ユーザーの入力データ
   */
  void handle(AdminUserUpdateInputData adminUserUpdateInputData);
}
