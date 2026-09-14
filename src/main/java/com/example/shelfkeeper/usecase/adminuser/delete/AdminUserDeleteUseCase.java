package com.example.shelfkeeper.usecase.adminuser.delete;

/**
 * 管理者ユーザーを削除するユースケース。
 *
 * @author itokanta
 */
public interface AdminUserDeleteUseCase {
  /**
   * 管理者ユーザーを削除する。
   *
   * @param adminUserDeleteInputData 削除する管理者ユーザーの入力データ
   */
  void handle(AdminUserDeleteInputData adminUserDeleteInputData);
}
