package com.example.shelfkeeper.usecase.user.delete;

/**
 * 利用者を削除するユースケース。
 *
 * @author itokanta
 */
public interface UseDeleteUseCase {
  /**
   * 利用者を削除する。
   *
   * @param userDeleteInputData 削除する利用者の入力データ
   */
  void handle(UserDeleteInputData userDeleteInputData);
}
