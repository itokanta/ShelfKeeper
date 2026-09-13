package com.example.shelfkeeper.usecase.adminuser.create;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.shelfkeeper.adapter.web.errorresponse.BadRequestException;
import com.example.shelfkeeper.domain.adminuser.AdminUser;
import com.example.shelfkeeper.usecase.port.AdminUserRepository;

/**
 * {@link AdminUserCreateUseCase} の実装クラス。
 * メールアドレスの重複を確認し、パスワードをハッシュ化したうえで管理者ユーザーを登録する。
 *
 * @author itokanta
 */
@Service
public class AdminUserCreateInteractor implements AdminUserCreateUseCase {
  /** 管理者ユーザーの永続化を担うリポジトリ。 */
  private final AdminUserRepository adminUserRepository;
  /** パスワードのハッシュ化に用いるエンコーダー。 */
  private final PasswordEncoder passwordEncoder;

  public AdminUserCreateInteractor(AdminUserRepository adminUserRepository, PasswordEncoder passwordEncoder) {
    this.adminUserRepository = adminUserRepository;
    this.passwordEncoder = passwordEncoder;
  }

  /**
   * 管理者ユーザーを作成する。
   * 同一メールアドレスの管理者が既に存在する場合は例外をスローする。
   *
   * @param adminUserCreateInputData 作成する管理者ユーザーの入力データ
   * @throws BadRequestException 同一メールアドレスの管理者が既に存在する場合
   */
  @Override
  public void handle(AdminUserCreateInputData adminUserCreateInputData) {
    Optional<AdminUser> adminUser = adminUserRepository.findByMail(adminUserCreateInputData.getMail());

    if (!adminUser.isEmpty()) {
      throw new BadRequestException("入力されたメールアドレスはすでに登録されています");
    }

    String hashPass = passwordEncoder.encode(adminUserCreateInputData.getPass());
    AdminUser newAdminUser = new AdminUser(null, adminUserCreateInputData.getName(), adminUserCreateInputData.getMail(),
        hashPass);

    adminUserRepository.adminUserCreate(newAdminUser);
  }
}
