package com.example.shelfkeeper.usecase.user.create;

import org.springframework.stereotype.Service;

import com.example.shelfkeeper.domain.user.User;
import com.example.shelfkeeper.usecase.port.UserRepository;

/**
 * {@link UserCreateUseCase} の実装クラス。
 * 入力された名前で利用者を登録する。
 *
 * @author itokanta
 */
@Service
public class UserCreateInteractor implements UserCreateUseCase {
  /** 利用者の永続化を担うリポジトリ。 */
  private final UserRepository userRepository;

  public UserCreateInteractor(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  /**
   * 利用者を作成する。
   *
   * @param userCreateInputData 作成する利用者の入力データ
   */
  @Override
  public void handle(UserCreateInputData userCreateInputData) {
    User user = new User(null, userCreateInputData.getName());
    userRepository.userCreate(user);
  }
}
