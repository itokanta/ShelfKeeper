package com.example.shelfkeeper.usecase.port;

import java.util.List;
import java.util.Optional;

import com.example.shelfkeeper.domain.user.User;

/**
 * 利用者の永続化を担うリポジトリ。
 *
 * @author itokanta
 */
public interface UserRepository {
  /**
   * 識別子で利用者を取得する。
   *
   * @param id 利用者の識別子
   * @return 該当する利用者。存在しない場合は空
   */
  Optional<User> findById(Integer id);

  /**
   * 利用者を全件取得する。
   *
   * @return 利用者の一覧
   */
  List<User> findAll();

  /**
   * 利用者を登録する。
   *
   * @param user 登録する利用者
   */
  void userCreate(User user);

  /**
   * 利用者を更新する。
   *
   * @param user 更新する利用者
   */
  void userUpdate(User user);

  /**
   * 利用者を削除する。
   *
   * @param id 削除する利用者の識別子
   */
  void userDelete(Integer id);
}
