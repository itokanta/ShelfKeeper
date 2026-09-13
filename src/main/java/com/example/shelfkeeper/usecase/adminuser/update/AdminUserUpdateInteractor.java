package com.example.shelfkeeper.usecase.adminuser.update;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.shelfkeeper.domain.adminuser.AdminUser;
import com.example.shelfkeeper.usecase.port.AdminUserRepository;

/**
 * {@link AdminUserUpdateUseCase} の実装クラス。
 * 指定された識別子の管理者ユーザーを取得し、入力された項目のみを更新する。
 * パスワードが指定された場合はハッシュ化して保存する。
 *
 * @author itokanta
 */
@Service
public class AdminUserUpdateInteractor implements AdminUserUpdateUseCase {
  /** 管理者ユーザーの永続化を担うリポジトリ。 */
  private final AdminUserRepository adminUserRepository;
  /** パスワードのハッシュ化に用いるエンコーダー。 */
  private final PasswordEncoder passwordEncoder;

  public AdminUserUpdateInteractor(AdminUserRepository adminUserRepository, PasswordEncoder passwordEncoder) {
    this.adminUserRepository = adminUserRepository;
    this.passwordEncoder = passwordEncoder;
  }

  /**
   * 管理者ユーザーを更新する。
   * 入力が {@code null} の項目は既存の値を維持する。
   *
   * @param adminUserUpdateInputData 更新する管理者ユーザーの入力データ
   */
  @Override
  public void handle(AdminUserUpdateInputData adminUserUpdateInputData) {
    AdminUser dbData = adminUserRepository.findById(adminUserUpdateInputData.getId());

    String name = dbData.getName();
    String mail = dbData.getMail();
    String pass = dbData.getPass();

    if (adminUserUpdateInputData.getName() != null) {
      name = adminUserUpdateInputData.getName();
    }

    if (adminUserUpdateInputData.getMail() != null) {
      mail = adminUserUpdateInputData.getMail();
    }

    if (adminUserUpdateInputData.getPass() != null) {
      pass = passwordEncoder.encode(adminUserUpdateInputData.getPass());
    }

    AdminUser newUser = new AdminUser(adminUserUpdateInputData.getId(), name, mail, pass);
    adminUserRepository.adminUserUpdate(newUser);
  }
}