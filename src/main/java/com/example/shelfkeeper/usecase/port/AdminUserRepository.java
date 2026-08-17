package com.example.shelfkeeper.usecase.port;

import java.util.Optional;

import com.example.shelfkeeper.domain.adminuser.AdminUser;

/**
 * 管理者ユーザーの永続化を担うリポジトリ。
 *
 * @author itokanta
 */
public interface AdminUserRepository {

  /**
   * 識別子で管理者ユーザーを取得する。
   *
   * @param id 管理者ユーザーの識別子
   * @return 該当する管理者ユーザー
   */
  AdminUser findById(Integer id);

  /**
   * メールアドレスで管理者ユーザーを取得する。
   *
   * @param mail メールアドレス
   * @return 該当する管理者ユーザー。存在しない場合は空
   */
  Optional<AdminUser> findByMail(String mail);

  /**
   * 管理者ユーザーを登録する。
   *
   * @param adminUser 登録する管理者ユーザー
   */
  void adminUserCreate(AdminUser adminUser);

  /**
   * 管理者ユーザーを更新する。
   *
   * @param adminUser 更新する管理者ユーザー
   */
  void adminUserUpdate(AdminUser adminUser);

  /**
   * 管理者ユーザーを削除する。
   *
   * @param id 削除する管理者ユーザーの識別子
   */
  void adminUserDelete(Integer id);
}