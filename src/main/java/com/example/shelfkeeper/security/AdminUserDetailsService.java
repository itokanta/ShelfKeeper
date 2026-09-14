package com.example.shelfkeeper.security;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.shelfkeeper.domain.adminuser.AdminUser;
import com.example.shelfkeeper.usecase.port.AdminUserRepository;

/**
 * 管理者ユーザーの認証情報を取得する {@link UserDetailsService} 実装。
 *
 * @author itokanta
 */
@Service
public class AdminUserDetailsService implements UserDetailsService {
  /** 管理者ユーザーの永続化を担うリポジトリ。 */
  private final AdminUserRepository adminUserRepository;

  public AdminUserDetailsService(AdminUserRepository adminUserRepository) {
    this.adminUserRepository = adminUserRepository;
  }

  /**
   * メールアドレスで管理者ユーザーを取得する。
   *
   * @param username メールアドレス
   * @return 認証情報
   * @throws UsernameNotFoundException 該当する管理者ユーザーが存在しない場合
   */
  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Optional<AdminUser> userCheck = adminUserRepository.findByMail(username);

    if (userCheck.isEmpty()) {
      throw new UsernameNotFoundException("ユーザーが見つかりません：" + username);
    }

    AdminUser loginUser = userCheck.get();

    return new LoginUser(loginUser);
  }
}
