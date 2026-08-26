package com.example.shelfkeeper.usecase.user.update;

import java.util.Optional;

import com.example.shelfkeeper.domain.user.User;
import com.example.shelfkeeper.usecase.port.UserRepository;

/**
 * {@link UserUpdateUseCase} の実装クラス。
 * 指定された識別子の利用者が存在することを確認し、入力された項目のみを更新する。
 *
 * @author itokanta
 */
public class UserUpdateInteractor implements UserUpdateUseCase{
  /** 利用者の永続化を担うリポジトリ。 */
  private final UserRepository userRepository;

  public UserUpdateInteractor(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  /**
   * 利用者を更新する。
   * 入力が {@code null} の項目は既存の値を維持する。
   * 指定された識別子の利用者が存在しない場合は例外をスローする。
   *
   * @param updateInputData 更新する利用者の入力データ
   * @throws RuntimeException 指定された利用者が存在しない場合
   */
  @Override
  public void handle(UserUpdateInputData updateInputData) {
    Optional<User> dbData = userRepository.findById(updateInputData.getId());

    if(dbData.isEmpty()){
      throw new RuntimeException("指定されたユーザーは存在しません");
    }

    String name = dbData.get().getName();

    if(updateInputData.getName() != null) {
      name = updateInputData.getName();
    }

    User newUser = new User(dbData.get().getId(), name);
    userRepository.userUpdate(newUser);
  }
}
