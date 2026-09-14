package com.example.shelfkeeper.infrastructure.book;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
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

import com.example.shelfkeeper.domain.book.Book;
import com.example.shelfkeeper.usecase.port.entity.book.BookStatus;

/**
 * {@link BookJdbcRepository} のテストクラス。
 *
 * @author itokanta
 */
@SpringBootTest
@Transactional
public class BookJdbcRepositoryTest {
  private final BookJdbcRepository bookJdbcRepository;
  private final NamedParameterJdbcTemplate template;

  @Autowired
  public BookJdbcRepositoryTest(BookJdbcRepository bookJdbcRepository, NamedParameterJdbcTemplate template) {
    this.bookJdbcRepository = bookJdbcRepository;
    this.template = template;
  }

  /**
   * 蔵書を登録し、タイトル検索と著者の再利用ができることを検証する。
   */
  @Test
  void bookCreateThenFindByIdTitle() {
    String titleUuid = makeUUID();
    String title1 = "title1-" + titleUuid;
    String title2 = "title2-" + titleUuid;
    String title3 = "title3-" + titleUuid;

    String authorNameUuid = makeUUID();
    String authorName1 = "testName1-" + authorNameUuid;
    String authorName2 = "testName2-" + authorNameUuid;

    bookJdbcRepository.bookCreate(new Book(null, title1, authorName1));
    bookJdbcRepository.bookCreate(new Book(null, title2, authorName1));
    bookJdbcRepository.bookCreate(new Book(null, title3, authorName2));

    List<BookStatus> result = bookJdbcRepository.findByTitle(titleUuid);
    int authorCount = findByAuthorName(List.of(authorName1, authorName2));

    BookStatus result1 = result.get(2);
    BookStatus result2 = result.get(1);
    BookStatus result3 = result.get(0);

    assertEquals(3, result.size());
    assertEquals(2, authorCount);
    assertTrue(result1.getTitle().contains(titleUuid));
    assertTrue(result2.getTitle().contains(titleUuid));
    assertTrue(result3.getTitle().contains(titleUuid));
    assertEquals(title1, result1.getTitle());
    assertEquals(title2, result2.getTitle());
    assertEquals(title3, result3.getTitle());
    assertEquals(authorName1, result1.getAuthorName());
    assertEquals(authorName1, result2.getAuthorName());
    assertEquals(authorName2, result3.getAuthorName());
  }

  /**
   * 蔵書を削除し、識別子で取得できなくなることを検証する。
   */
  @Test
  void bookDeleteThenFinById() {
    String title = "title" + makeUUID();
    String authorName = "authorName" + makeUUID();
    int bookId = bookCreateReturnId(title, authorName);

    Book book = bookJdbcRepository.findById(bookId).orElseThrow();
    bookJdbcRepository.bookDelete(bookId);

    assertEquals(bookId, book.getId());
    assertEquals(title, book.getTitle());
    assertEquals(authorName, book.getAuthorName());
    assertTrue(bookJdbcRepository.findById(bookId).isEmpty());
  }

  /**
   * 全件取得で貸出中と返却済みの蔵書の状態を区別できることを検証する。
   */
  @Test
  void findAllSuccess() {
    String userName1 = "user1" + makeUUID();
    String userName2 = "user2" + makeUUID();
    String title1 = "title1" + makeUUID();
    String title2 = "title2" + makeUUID();
    String authorName1 = "author1" + makeUUID();
    String authorName2 = "author2" + makeUUID();

    LocalDate loanDate1 = LocalDate.of(2026, 8, 15);
    LocalDate loanDate2 = LocalDate.of(2026, 9, 1);
    LocalDate dueDate1 = LocalDate.of(2026, 8, 20);
    LocalDate dueDate2 = LocalDate.of(2026, 9, 5);
    LocalDate returnDate1 = LocalDate.of(2026, 8, 19);

    int userId1 = userCreateReturnId(userName1);
    int userId2 = userCreateReturnId(userName2);
    int bookId1 = bookCreateReturnId(title1, authorName1);
    int bookId2 = bookCreateReturnId(title2, authorName2);
    loanRecordCreate(userId1, bookId1, loanDate1, dueDate1, returnDate1);
    loanRecordCreate(userId2, bookId2, loanDate2, dueDate2, null);

    List<BookStatus>result = bookJdbcRepository.findAll();
    BookStatus result1 = result.stream()
        .filter(book -> book.getId().equals(bookId1))
        .findFirst()
        .orElseThrow();
    BookStatus result2 = result.stream()
        .filter(book -> book.getId().equals(bookId2))
        .findFirst()
        .orElseThrow();

    assertEquals(bookId1, result1.getId());
    assertEquals(title1, result1.getTitle());
    assertEquals(authorName1, result1.getAuthorName());
    assertTrue(result1.getStatus());
    assertEquals(bookId2, result2.getId());
    assertEquals(title2, result2.getTitle());
    assertEquals(authorName2, result2.getAuthorName());
    assertFalse(result2.getStatus());
  }

  private String makeUUID() {
    return "test-" + UUID.randomUUID();
  }

  private int findByAuthorName(List<String> authorNameList) {
    String sql = "SELECT COUNT(*) FROM authors WHERE name IN (:names);";
    SqlParameterSource param = new MapSqlParameterSource().addValue("names", authorNameList);
    return template.queryForObject(sql, param, Integer.class);
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

  private void loanRecordCreate(int userId, int bookId, LocalDate loanDate, LocalDate dueDate, LocalDate returnDate) {
    String sql = "INSERT INTO loan_records (user_id, book_id, loan_date, due_date, return_date) VALUES "
                + "(:userId, :bookId, :loanDate, :dueDate, :returnDate);";
    SqlParameterSource param = new MapSqlParameterSource()
                .addValue("userId", userId)
                .addValue("bookId", bookId)
                .addValue("loanDate", loanDate)
                .addValue("dueDate", dueDate)
                .addValue("returnDate", returnDate);
    template.update(sql, param);
  }
}
