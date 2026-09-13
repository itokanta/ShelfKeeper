package com.example.shelfkeeper.infrastructure.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.transaction.annotation.Transactional;

import com.example.shelfkeeper.domain.user.User;
import com.example.shelfkeeper.usecase.port.entity.book.UserOverDueBook;
import com.example.shelfkeeper.usecase.port.entity.user.UserOverDue;

/**
 * {@link UserJdbcRepository} のテストクラス。
 *
 * @author itokanta
 */
@SpringBootTest
@Transactional
public class UserJdbcRepositoryTest {
  private final UserJdbcRepository userJdbcRepository;
  private final NamedParameterJdbcTemplate template;

  @Autowired
  public UserJdbcRepositoryTest(UserJdbcRepository userJdbcRepository, NamedParameterJdbcTemplate template) {
    this.userJdbcRepository = userJdbcRepository;
    this.template = template;
  }

  /**
   * 利用者を登録し、名前順で一覧取得できることを検証する。
   */
  @Test
  void userCreateThenFindAll() {
    String userName1 = "user1-" + makeUUID();
    String userName2 = "user2-" + makeUUID();

    userJdbcRepository.userCreate(new User(null, userName1));
    userJdbcRepository.userCreate(new User(null, userName2));

    List<User> result = userJdbcRepository.findAll();

    User user1 = result.stream()
        .filter(user -> user.getName().equals(userName1))
        .findFirst()
        .orElseThrow();
    User user2 = result.stream()
        .filter(user -> user.getName().equals(userName2))
        .findFirst()
        .orElseThrow();

    assertTrue(result.indexOf(user1) < result.indexOf(user2));
    assertEquals(userName1, user1.getName());
    assertEquals(userName2, user2.getName());
  }

  /**
   * 利用者の更新と削除、識別子による取得ができることを検証する。
   */
  @Test
  void findByIdAndUserUpdateAndUserDeleteSuccess() {
    String createUserName = "user-create-" + makeUUID();
    String updateUserName = "user-update-" + makeUUID();

    userJdbcRepository.userCreate(new User(null, createUserName));

    List<User> result = userJdbcRepository.findAll();

    User createdUser = result.stream()
        .filter(user -> user.getName().equals(createUserName))
        .findFirst()
        .orElseThrow();

    userJdbcRepository.userUpdate(new User(createdUser.getId(), updateUserName));

    User updatedUser = userJdbcRepository.findById(createdUser.getId()).orElseThrow();
    userJdbcRepository.userDelete(createdUser.getId());

    assertEquals(updateUserName, updatedUser.getName());
    assertTrue(userJdbcRepository.findById(createdUser.getId()).isEmpty());
  }

  /**
   * 延滞中の利用者とその延滞書籍一覧を取得できることを検証する。
   */
  @Test
  void findOverDueUserSuccess() {
    String userName1 = "user1-" + makeUUID();
    String userName2 = "user2-" + makeUUID();
    String userName3 = "user3-" + makeUUID();
    String bookTitle1 = "title1-" + makeUUID();
    String bookTitle2 = "title2-" + makeUUID();
    String bookTitle3 = "title3-" + makeUUID();
    String bookTitle4 = "title4-" + makeUUID();

    LocalDate loanDate1 = LocalDate.of(2025, 8, 15);
    LocalDate dueDate1 = LocalDate.of(2025, 8, 20);
    LocalDate returnDate1 = LocalDate.of(2025, 8, 19);

    LocalDate loanDate2 = LocalDate.of(2025, 8, 21);
    LocalDate dueDate2 = LocalDate.of(2025, 8, 26);

    LocalDate loanDate3 = LocalDate.of(2025, 9, 1);
    LocalDate dueDate3 = LocalDate.of(2025, 9, 6);

    LocalDate loanDate4 = LocalDate.of(2025, 9, 15);
    LocalDate dueDate4 = LocalDate.of(2025, 9, 20);

    LocalDate loanDate5 = LocalDate.of(2025, 9, 21);
    LocalDate dueDate5 = LocalDate.of(2025, 9, 26);

    int userId1 = userCreateReturnId(userName1);
    int userId2 = userCreateReturnId(userName2);
    int userId3 = userCreateReturnId(userName3);

    int bookId1 = bookCreateReturnId(bookTitle1, "author1-" + makeUUID());
    int bookId2 = bookCreateReturnId(bookTitle2, "author2-" + makeUUID());
    int bookId3 = bookCreateReturnId(bookTitle3, "author3-" + makeUUID());
    int bookId4 = bookCreateReturnId(bookTitle4, "author4-" + makeUUID());

    loanRecordCreate(userId1, bookId1, loanDate1, dueDate1, returnDate1);
    loanRecordCreate(userId2, bookId1, loanDate2, dueDate2, null);
    loanRecordCreate(userId2, bookId2, loanDate3, dueDate3, null);
    loanRecordCreate(userId3, bookId3, loanDate4, dueDate4, null);
    loanRecordCreate(userId3, bookId4, loanDate5, dueDate5, null);

    List<UserOverDue> result = userJdbcRepository.findOverDueUser();

    UserOverDue user2 = result.stream()
        .filter(user -> user.getUserId().equals(userId2))
        .findFirst()
        .orElseThrow();
    UserOverDue user3 = result.stream()
        .filter(user -> user.getUserId().equals(userId3))
        .findFirst()
        .orElseThrow();

    UserOverDueBook loan2 = user2.getDueBookList().get(0);
    UserOverDueBook loan3 = user2.getDueBookList().get(1);
    UserOverDueBook loan4 = user3.getDueBookList().get(0);
    UserOverDueBook loan5 = user3.getDueBookList().get(1);

    assertTrue(result.stream().noneMatch(user -> user.getUserId().equals(userId1)));
    assertTrue(result.indexOf(user2) < result.indexOf(user3));

    assertEquals(userId2, user2.getUserId());
    assertEquals(userName2, user2.getUserName());
    assertEquals(2, user2.getDueBookList().size());
    assertEquals(userId3, user3.getUserId());
    assertEquals(userName3, user3.getUserName());
    assertEquals(2, user3.getDueBookList().size());

    assertEquals(bookId1, loan2.getBookId());
    assertEquals(bookTitle1, loan2.getBookTitle());
    assertEquals(loanDate2, loan2.getLoanDate());
    assertEquals(dueDate2, loan2.getDueDate());
    assertEquals(bookId2, loan3.getBookId());
    assertEquals(bookTitle2, loan3.getBookTitle());
    assertEquals(loanDate3, loan3.getLoanDate());
    assertEquals(dueDate3, loan3.getDueDate());

    assertEquals(bookId3, loan4.getBookId());
    assertEquals(bookTitle3, loan4.getBookTitle());
    assertEquals(loanDate4, loan4.getLoanDate());
    assertEquals(dueDate4, loan4.getDueDate());
    assertEquals(bookId4, loan5.getBookId());
    assertEquals(bookTitle4, loan5.getBookTitle());
    assertEquals(loanDate5, loan5.getLoanDate());
    assertEquals(dueDate5, loan5.getDueDate());
  }

  private String makeUUID() {
    return "test-" + UUID.randomUUID();
  }

  private int bookCreateReturnId(String title, String authorName) {
    String sql = "INSERT INTO books (title, author_id) VALUES (:title, :authorId) RETURNING id;";
    SqlParameterSource param = new MapSqlParameterSource()
        .addValue("title", title)
        .addValue("authorId", authorCreateReturnId(authorName));
    return template.queryForObject(sql, param, Integer.class);
  }

  private int authorCreateReturnId(String authorName) {
    String sql = "INSERT INTO authors (name) VALUES (:name) RETURNING id;";
    SqlParameterSource param = new MapSqlParameterSource().addValue("name", authorName);
    return template.queryForObject(sql, param, Integer.class);
  }

  private int userCreateReturnId(String userName) {
    String sql = "INSERT INTO users (name) VALUES (:name) RETURNING id;";
    SqlParameterSource param = new MapSqlParameterSource().addValue("name", userName);
    return template.queryForObject(sql, param, Integer.class);
  }

  private int loanRecordCreate(int userId, int bookId, LocalDate loanDate, LocalDate dueDate, LocalDate returnDate) {
    String sql = "INSERT INTO loan_records (user_id, book_id, loan_date, due_date, return_date) VALUES "
        + "(:userId, :bookId, :loanDate, :dueDate, :returnDate) RETURNING id;";
    SqlParameterSource param = new MapSqlParameterSource()
        .addValue("userId", userId)
        .addValue("bookId", bookId)
        .addValue("loanDate", loanDate)
        .addValue("dueDate", dueDate)
        .addValue("returnDate", returnDate);
    return template.queryForObject(sql, param, Integer.class);
  }
}
