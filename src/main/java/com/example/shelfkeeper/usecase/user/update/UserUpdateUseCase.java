package com.example.shelfkeeper.usecase.user.update;

/**
 * 利用者を更新するユースケース。
 *
 * @author itokanta
 */
public interface UserUpdateUseCase {
  /**
   * 利用者を更新する。
   *
   * @param updateInputData 更新する利用者の入力データ
   */
  void handle(UserUpdateInputData updateInputData);
}
