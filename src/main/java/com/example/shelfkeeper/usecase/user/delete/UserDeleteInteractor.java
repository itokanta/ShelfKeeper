package com.example.shelfkeeper.usecase.user.delete;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.shelfkeeper.adapter.web.errorresponse.BadRequestException;
import com.example.shelfkeeper.domain.user.User;
import com.example.shelfkeeper.usecase.port.UserRepository;

/**
 * {@link UserDeleteUseCase} の実装クラス。
 * 指定された識別子の利用者が存在することを確認したうえで削除する。
 *
 * @author itokanta
 */
@Service
public class UserDeleteInteractor implements UserDeleteUseCase{
  /** 利用者の永続化を担うリポジトリ。 */
  private final UserRepository userRepository;

  public UserDeleteInteractor(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  /**
   * 利用者を削除する。
   * 指定された識別子の利用者が存在しない場合は例外をスローする。
   *
   * @param userDeleteInputData 削除する利用者の入力データ
   * @throws BadRequestException 指定された利用者が存在しない場合
   */
  @Override
  public void handle(UserDeleteInputData userDeleteInputData) {
    Integer deleteTargetId = userDeleteInputData.getId();
    Optional<User> deleteTarget = userRepository.findById(deleteTargetId);

    if(deleteTarget.isEmpty()){
      throw new BadRequestException("指定されたユーザーは存在しません");
    }

    userRepository.userDelete(deleteTargetId);
  }
}
