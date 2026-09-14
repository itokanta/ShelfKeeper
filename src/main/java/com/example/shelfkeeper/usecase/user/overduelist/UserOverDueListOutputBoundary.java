package com.example.shelfkeeper.usecase.user.overduelist;

/**
 * 延滞利用者一覧取得ユースケースの出力境界。
 *
 * @author itokanta
 */
public interface UserOverDueListOutputBoundary {
  /**
   * 取得結果を後続処理へ引き渡す。
   *
   * @param userOverDueListOutputData 取得した延滞利用者一覧の出力データ
   */
  void complete(UserOverDueListOutputData userOverDueListOutputData);
}
