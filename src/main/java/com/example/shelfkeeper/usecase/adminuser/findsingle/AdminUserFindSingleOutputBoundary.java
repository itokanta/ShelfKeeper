package com.example.shelfkeeper.usecase.adminuser.findsingle;

/**
 * 管理者ユーザー1件取得ユースケースの出力境界。
 *
 * @author itokanta
 */
public interface AdminUserFindSingleOutputBoundary {
  /**
   * 取得結果を後続処理へ引き渡す。
   *
   * @param adminUserFindSingleOutputData 取得した管理者ユーザーの出力データ
   */
  void complete(AdminUserFindSingleOutputData adminUserFindSingleOutputData);
}