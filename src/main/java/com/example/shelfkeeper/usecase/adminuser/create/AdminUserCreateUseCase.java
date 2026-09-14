package com.example.shelfkeeper.usecase.adminuser.create;

/**
 * 管理者ユーザーを作成するユースケース。
 *
 * @author itokanta
 */
public interface AdminUserCreateUseCase {
  /**
   * 管理者ユーザーを作成する。
   *
   * @param adminUserCreateInputData 作成する管理者ユーザーの入力データ
   */
  void handle(AdminUserCreateInputData adminUserCreateInputData);
}
