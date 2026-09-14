package com.example.shelfkeeper.usecase.user.overduelist;

/**
 * 延滞利用者一覧取得ユースケース。
 *
 * @author itokanta
 */
public interface UserOverDueListUseCase {
  /**
   * 延滞中の利用者一覧を取得する。
   */
  void handle();
}
