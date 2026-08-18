package com.example.shelfkeeper.usecase.adminuser.findsingle;

/**
 * 管理者ユーザーを1件取得するユースケース。
 *
 * @author itokanta
 */
public interface AdminUserFindSingleUseCase {
  /**
   * 管理者ユーザーを1件取得する。
   *
   * @param adminUserFindSingleInputData 取得する管理者ユーザーの入力データ
   */
  void handle(AdminUserFindSingleInputData adminUserFindSingleInputData);
}
