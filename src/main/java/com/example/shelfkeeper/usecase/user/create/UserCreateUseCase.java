package com.example.shelfkeeper.usecase.user.create;

/**
 * 利用者を作成するユースケース。
 *
 * @author itokanta
 */
public interface UserCreateUseCase {
  /**
   * 利用者を作成する。
   *
   * @param userCreateInputData 作成する利用者の入力データ
   */
  void handle(UserCreateInputData userCreateInputData);
}
