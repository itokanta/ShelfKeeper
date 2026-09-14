package com.example.shelfkeeper.infrastructure.loanrecord;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.transaction.annotation.Transactional;

import com.example.shelfkeeper.domain.loanrecord.LoanRecord;
import com.example.shelfkeeper.usecase.port.entity.loanrecord.BookHistory;

/**
 * {@link LoanRecordJdbcRepository} のテストクラス。
 *
 * @author itokanta
 */
@SpringBootTest
@Transactional
public class LoanRecordJdbcRepositoryTest {
  private final LoanRecordJdbcRepository loanRecordJdbcRepository;
  private final NamedParameterJdbcTemplate template;

  private String bookTitle;
  private String authorName;
  private String userName;
  private int bookId;
  private int userId;
  private LocalDate loanDate;
  private LocalDate dueDate;

  private static final RowMapper<LoanRecord> LOAN_ROW_MAPPER = (rs, i) -> {
    LocalDate returnDate = rs.getObject("return_date", LocalDate.class);
    LoanRecord loanRecord = new LoanRecord(
      rs.getInt("loan_id"),
      rs.getInt("user_id"),
      rs.getInt("book_id"),
      rs.getObject("loan_date", LocalDate.class),
      rs.getObject("due_date", LocalDate.class),
      returnDate != null ? returnDate : null);
    return loanRecord;
  };

  @Autowired
  public LoanRecordJdbcRepositoryTest(LoanRecordJdbcRepository loanRecordJdbcRepository,
      NamedParameterJdbcTemplate template) {
    this.loanRecordJdbcRepository = loanRecordJdbcRepository;
    this.template = template;
  }

  @BeforeEach
  void setUp() {
    bookTitle = "title-" + makeUUID();
    authorName = "author-" + makeUUID();
    userName = "user-" + makeUUID();
    bookId = bookCreateReturnId(bookTitle, authorName);
    userId = userCreateReturnId(userName);
    loanDate = LocalDate.of(2026, 8, 15);
    dueDate = LocalDate.of(2026, 8, 20);
  }

  /**
   * 貸出を登録したあと返却し、返却日が記録されることを検証する。
   */
  @Test
  void checkOutAndCheckInSuccess() {
    loanRecordJdbcRepository.checkOut(new LoanRecord(null, userId, bookId, loanDate, dueDate, null));
    LoanRecord checkOutResult = findByBookTitle(bookTitle);

    LocalDate returnDate = LocalDate.of(2026, 8, 19);
    loanRecordJdbcRepository.checkIn(new LoanRecord(checkOutResult.getId(), userId, bookId, loanDate, dueDate, returnDate));
    LoanRecord checkInResult = loanRecordJdbcRepository.findById(checkOutResult.getId()).orElseThrow();

    assertEquals(bookId, checkOutResult.getBookId());
    assertEquals(userId, checkOutResult.getUserId());
    assertEquals(loanDate, checkOutResult.getLoanDate());
    assertEquals(dueDate, checkOutResult.getDueDate());
    assertNull(checkOutResult.getReturnDate());
    assertEquals(bookId, checkInResult.getBookId());
    assertEquals(userId, checkInResult.getUserId());
    assertEquals(loanDate, checkInResult.getLoanDate());
    assertEquals(dueDate, checkInResult.getDueDate());
    assertEquals(returnDate, checkInResult.getReturnDate());
  }

  /**
   * 書籍の識別子で貸出履歴を取得できることを検証する。
   */
  @Test
  void findByBookIdSuccess() {
    int loanId1 = loanRecordCreate(userId, bookId, loanDate, dueDate, LocalDate.of(2026, 8, 19));
    int loanId2 = loanRecordCreate(userId, bookId, loanDate.plusDays(3), dueDate.plusDays(3), null);

    List<BookHistory> result = loanRecordJdbcRepository.findByBookId(bookId);
    BookHistory result1 = result.get(1);
    BookHistory result2 = result.get(0);

    assertEquals(2, result.size());
    assertEquals(loanId1, result1.getLoanRecordId());
    assertEquals(userName, result1.getUserName());
    assertEquals(bookTitle, result1.getBookName());
    assertEquals(loanDate, result1.getLoanDate());
    assertEquals(dueDate, result1.getDueDate());
    assertEquals(LocalDate.of(2026, 8, 19), result1.getReturnDate());
    assertEquals(loanId2, result2.getLoanRecordId());
    assertEquals(userName, result2.getUserName());
    assertEquals(bookTitle, result2.getBookName());
    assertEquals(loanDate.plusDays(3), result2.getLoanDate());
    assertEquals(dueDate.plusDays(3), result2.getDueDate());
    assertNull(result2.getReturnDate());
  }

  /**
   * 存在しない識別子では貸出記録を取得できないことを検証する。
   */
  @Test
  void findByIdIsEmpty() {
    int loanId = loanRecordCreate(userId, bookId, loanDate, dueDate, null);
    assertTrue(loanRecordJdbcRepository.findById(loanId + 1).isEmpty());
  }

  /**
   * 未返却の貸出記録を取得し、返却後は取得できなくなることを検証する。
   */
  @Test
  void returnDateIsNullFindByBookIdSuccess() {
    int loanId1 = loanRecordCreate(userId, bookId, loanDate, dueDate, null);
    LoanRecord result = loanRecordJdbcRepository.returnDateIsNullFindByBookId(bookId).orElseThrow();
    loanRecordJdbcRepository.checkIn(new LoanRecord(result.getId(), userId, bookId, loanDate, dueDate, LocalDate.of(2026, 8, 19)));

    assertEquals(loanId1, result.getId());
    assertEquals(userId, result.getUserId());
    assertEquals(bookId, result.getBookId());
    assertEquals(loanDate, result.getLoanDate());
    assertEquals(dueDate, result.getDueDate());
    assertNull(result.getReturnDate());
    assertTrue(loanRecordJdbcRepository.returnDateIsNullFindByBookId(bookId).isEmpty());
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

  private LoanRecord findByBookTitle(String title) {
    String sql = "SELECT "
        + "l.id AS loan_id, "
        + "l.user_id AS user_id, "
        + "l.book_id AS book_id, "
        + "l.loan_date AS loan_date, "
        + "l.due_date AS due_date, "
        + "l.return_date AS return_date "
        + "FROM loan_records l "
        + "JOIN books b ON l.book_id = b.id "
        + "WHERE b.title = :title;";
    SqlParameterSource param = new MapSqlParameterSource().addValue("title", title);

    return template.queryForObject(sql, param, LOAN_ROW_MAPPER);
  }
}
