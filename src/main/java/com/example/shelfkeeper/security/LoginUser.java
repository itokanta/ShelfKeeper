package com.example.shelfkeeper.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.shelfkeeper.domain.adminuser.AdminUser;

/**
 * 認証済み管理者ユーザーを表す {@link UserDetails} 実装。
 *
 * @author itokanta
 */
public class LoginUser implements UserDetails{
  /** 管理者ユーザー。 */
  private final AdminUser adminUser;

  public LoginUser(AdminUser adminUser) {
    this.adminUser = adminUser;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return AuthorityUtils.createAuthorityList("ROLE_ADMIN");
  }

  @Override
  public String getPassword() {
    return adminUser.getPass();
  }

  @Override
  public String getUsername() {
    return adminUser.getMail();
  }

  public AdminUser getAdminUser() {
    return adminUser;
  }
}
