package com.example.shelfkeeper.usecase.user.list;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.shelfkeeper.domain.user.User;
import com.example.shelfkeeper.usecase.port.UserRepository;

/**
 * {@link UserListUseCase} の実装クラス。
 * 利用者を全件取得し、出力境界へ引き渡す。
 *
 * @author itokanta
 */
@Service
public class UserListInteractor implements UserListUseCase{
  /** 利用者の永続化を担うリポジトリ。 */
  private final UserRepository userRepository;
  /** 取得結果を後続処理へ引き渡す出力境界。 */
  private final UserListOutputBoundary userListOutputBoundary;

  public UserListInteractor(UserRepository userRepository, UserListOutputBoundary userListOutputBoundary) {
    this.userRepository = userRepository;
    this.userListOutputBoundary = userListOutputBoundary;
  }

  /**
   * 利用者を全件取得する。
   */
  @Override
  public void handle() {
    List<User> userList = userRepository.findAll();
    List<UserListItemOutputData> outputData = new ArrayList<>();

    for(User user : userList) {
      outputData.add(new UserListItemOutputData(user.getId(), user.getName()));
    }

    userListOutputBoundary.complete(new UserListOutputData(outputData));
  }
}
