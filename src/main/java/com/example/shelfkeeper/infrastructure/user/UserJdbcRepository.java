package com.example.shelfkeeper.infrastructure.user;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.shelfkeeper.domain.user.User;
import com.example.shelfkeeper.usecase.port.UserRepository;
import com.example.shelfkeeper.usecase.port.entity.book.UserOverDueBook;
import com.example.shelfkeeper.usecase.port.entity.user.UserOverDue;

/**
 * {@link UserRepository} の JDBC 実装クラス。
 *
 * @author itokanta
 */
@Repository
public class UserJdbcRepository implements UserRepository {
  /** JDBC テンプレート。 */
  private final NamedParameterJdbcTemplate template;

  /** 利用者の行マッパー。 */
  private static final RowMapper<User> USER_ROW_MAPPER = (rs, i) -> {
    User user = new User(rs.getInt("id"), rs.getString("name"));
    return user;
  };

  /** 延滞利用者とその延滞書籍一覧の抽出器。 */
  private static final ResultSetExtractor<List<UserOverDue>> USER_OVER_DUE_EXTRACTOR = (rs) -> {
    Map<Integer, UserOverDue> resultMap = new LinkedHashMap<>();

    while (rs.next()) {
      Integer userId = rs.getInt("user_id");
      UserOverDueBook userOverDueBook = new UserOverDueBook(
          rs.getInt("book_id"),
          rs.getString("book_title"),
          rs.getObject("loan_date", LocalDate.class),
          rs.getObject("due_date", LocalDate.class));

      if (resultMap.containsKey(userId)) {
        resultMap.get(userId).getDueBookList().add(userOverDueBook);
        continue;
      }

      List<UserOverDueBook> userOverDueBookList = new ArrayList<>();
      userOverDueBookList.add(userOverDueBook);

      UserOverDue userOverDue = new UserOverDue(
          userId,
          rs.getString("user_name"),
          userOverDueBookList);

      resultMap.put(userId, userOverDue);
    }
    return new ArrayList<>(resultMap.values());
  };

  public UserJdbcRepository(NamedParameterJdbcTemplate template) {
    this.template = template;
  }

  /**
   * 利用者を全件取得する。
   *
   * @return 利用者の一覧
   */
  @Override
  public List<User> findAll() {
    String sql = "SELECT id, name FROM users ORDER BY name;";
    return template.query(sql, USER_ROW_MAPPER);
  }

  /**
   * 識別子で利用者を取得する。
   *
   * @param id 利用者の識別子
   * @return 該当する利用者。存在しない場合は空
   */
  @Override
  public Optional<User> findById(Integer id) {
    String sql = "SELECT id, name FROM users WHERE id = :id;";
    SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);
    List<User> result = template.query(sql, param, USER_ROW_MAPPER);

    return result.isEmpty() ? Optional.empty() : Optional.of(result.get(0));
  }

  /**
   * 延滞中の利用者とその延滞書籍一覧を取得する。
   *
   * @return 延滞利用者の一覧
   */
  @Override
  public List<UserOverDue> findOverDueUser() {
    String sql = "SELECT "
        + "u.id AS user_id, "
        + "u.name AS user_name, "
        + "b.id AS book_id, "
        + "b.title AS book_title, "
        + "l.loan_date AS loan_date, "
        + "l.due_date AS due_date "
        + "FROM users u "
        + "JOIN loan_records l ON u.id = l.user_id "
        + "JOIN books b ON l.book_id = b.id "
        + "WHERE l.return_date IS NULL AND l.due_date < CURRENT_DATE "
        + "ORDER BY l.due_date, l.id;";
    return template.query(sql, USER_OVER_DUE_EXTRACTOR);
  }

  /**
   * 利用者を登録する。
   *
   * @param user 登録する利用者
   */
  @Override
  public void userCreate(User user) {
    String sql = "INSERT INTO users (name) VALUES (:name);";
    SqlParameterSource param = new MapSqlParameterSource().addValue("name", user.getName());
    template.update(sql, param);
  }

  /**
   * 利用者を削除する。
   *
   * @param id 削除する利用者の識別子
   */
  @Override
  public void userDelete(Integer id) {
    String sql = "DELETE FROM users WHERE id = :id;";
    SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);
    template.update(sql, param);
  }

  /**
   * 利用者を更新する。
   *
   * @param user 更新する利用者
   */
  @Override
  public void userUpdate(User user) {
    String sql = "UPDATE users SET name = :name WHERE id = :id;";
    SqlParameterSource param = new MapSqlParameterSource()
        .addValue("name", user.getName())
        .addValue("id", user.getId());

    template.update(sql, param);
  }
}
