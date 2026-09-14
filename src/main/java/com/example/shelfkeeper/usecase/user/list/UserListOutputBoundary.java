package com.example.shelfkeeper.usecase.user.list;

/**
 * 利用者一覧取得ユースケースの出力境界。
 *
 * @author itokanta
 */
public interface UserListOutputBoundary {
  /**
   * 取得結果を後続処理へ引き渡す。
   *
   * @param userListOutputData 取得した利用者一覧の出力データ
   */
  void complete(UserListOutputData userListOutputData);
}
