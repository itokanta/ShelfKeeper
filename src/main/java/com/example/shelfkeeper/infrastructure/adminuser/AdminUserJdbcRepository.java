package com.example.shelfkeeper.infrastructure.adminuser;

import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.shelfkeeper.domain.adminuser.AdminUser;
import com.example.shelfkeeper.usecase.port.AdminUserRepository;

/**
 * {@link AdminUserRepository} の JDBC 実装クラス。
 *
 * @author itokanta
 */
@Repository
public class AdminUserJdbcRepository implements AdminUserRepository {
  /** JDBC テンプレート。 */
  private final NamedParameterJdbcTemplate template;

  /** 管理者ユーザーの行マッパー。 */
  private static final RowMapper<AdminUser> ROW_MAPPER = (rs, i) -> {
    AdminUser adminUser = new AdminUser(
        rs.getInt("id"),
        rs.getString("name"),
        rs.getString("mail"),
        rs.getString("pass"));
    return adminUser;
  };

  public AdminUserJdbcRepository(NamedParameterJdbcTemplate template) {
    this.template = template;
  }

  /**
   * 管理者ユーザーを登録する。
   *
   * @param adminUser 登録する管理者ユーザー
   */
  @Override
  public void adminUserCreate(AdminUser adminUser) {
    String sql = "INSERT INTO admin_users (name, mail, pass) VALUES (:name, :mail, :pass);";

    SqlParameterSource param = new MapSqlParameterSource()
        .addValue("name", adminUser.getName())
        .addValue("mail", adminUser.getMail())
        .addValue("pass", adminUser.getPass());

    template.update(sql, param);
  }

  /**
   * 管理者ユーザーを削除する。
   *
   * @param id 削除する管理者ユーザーの識別子
   */
  @Override
  public void adminUserDelete(Integer id) {
    String sql = "DELETE FROM admin_users WHERE id = :id;";
    SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);
    template.update(sql, param);
  }

  /**
   * 管理者ユーザーを更新する。
   *
   * @param adminUser 更新する管理者ユーザー
   */
  @Override
  public void adminUserUpdate(AdminUser adminUser) {
    String sql = "UPDATE admin_users SET name = :name, mail = :mail, pass = :pass WHERE id = :id;";

    SqlParameterSource param = new MapSqlParameterSource()
        .addValue("id", adminUser.getId())
        .addValue("name", adminUser.getName())
        .addValue("mail", adminUser.getMail())
        .addValue("pass", adminUser.getPass());

    template.update(sql, param);
  }

  /**
   * 識別子で管理者ユーザーを取得する。
   *
   * @param id 管理者ユーザーの識別子
   * @return 該当する管理者ユーザー
   */
  @Override
  public AdminUser findById(Integer id) {
    String sql = "SELECT id, name, mail, pass FROM admin_users WHERE id = :id;";
    SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);
    return template.queryForObject(sql, param, ROW_MAPPER);
  }

  /**
   * メールアドレスで管理者ユーザーを取得する。
   *
   * @param mail メールアドレス
   * @return 該当する管理者ユーザー。存在しない場合は空
   */
  @Override
  public Optional<AdminUser> findByMail(String mail) {
    String sql = "SELECT id, name, mail, pass FROM admin_users WHERE mail = :mail;";
    SqlParameterSource param = new MapSqlParameterSource().addValue("mail", mail);
    List<AdminUser> result = template.query(sql, param, ROW_MAPPER);

    return result.isEmpty() ? Optional.empty() : Optional.of(result.get(0));
  }
}
