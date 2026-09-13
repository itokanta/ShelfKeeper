package com.example.shelfkeeper.infrastructure.book;

import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.shelfkeeper.domain.book.Book;
import com.example.shelfkeeper.usecase.port.BookRepository;
import com.example.shelfkeeper.usecase.port.entity.book.BookStatus;

/**
 * {@link BookRepository} の JDBC 実装クラス。
 *
 * @author itokanta
 */
@Repository
public class BookJdbcRepository implements BookRepository {
  /** JDBC テンプレート。 */
  private final NamedParameterJdbcTemplate template;

  /** 著者の行マッパー。 */
  private static final RowMapper<Author> AUTHOR_ROW_MAPPER = (rs, i) -> {
    Author author = new Author(rs.getInt("id"), rs.getString("name"));
    return author;
  };

  /** 蔵書の行マッパー。 */
  private static final RowMapper<Book> BOOK_ROW_MAPPER = (rs, i) -> {
    Book book = new Book(rs.getInt("book_id"), rs.getString("book_title"), rs.getString("author_name"));
    return book;
  };

  /** 蔵書と貸出状態の行マッパー。 */
  private static final RowMapper<BookStatus> BOOK_STATUS_ROW_MAPPER = (rs, i) -> {
    BookStatus bookStatus = new BookStatus(
        rs.getInt("book_id"),
        rs.getString("book_title"),
        rs.getString("author_name"),
        rs.getObject("loan_id", Integer.class) == null);
    return bookStatus;
  };

  public BookJdbcRepository(NamedParameterJdbcTemplate template) {
    this.template = template;
  }

  /**
   * 蔵書を登録する。
   * 著者が未登録の場合は先に著者を登録する。
   *
   * @param book 登録する蔵書
   */
  @Override
  public void bookCreate(Book book) {
    String sql = "INSERT INTO books (title, author_id) VALUES (:title, :authorId);";

    Optional<Author> findResult = findByAuthorName(book.getAuthorName());
    Integer authorId;

    if (findResult.isEmpty()) {
      authorId = createAuthorReturnId(new Author(null, book.getAuthorName()));
    } else {
      authorId = findResult.get().getId();
    }

    SqlParameterSource param = new MapSqlParameterSource()
        .addValue("title", book.getTitle())
        .addValue("authorId", authorId);
    template.update(sql, param);
  }

  /**
   * 蔵書を削除する。
   *
   * @param id 削除する書籍の識別子
   */
  @Override
  public void bookDelete(Integer id) {
    String sql = "DELETE FROM books WHERE id = :id;";
    SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);
    template.update(sql, param);
  }

  /**
   * 蔵書を貸出状態付きで全件取得する。
   *
   * @return 蔵書と貸出状態の一覧
   */
  @Override
  public List<BookStatus> findAll() {
    String sql = "SELECT "
        + "b.id AS book_id, "
        + "b.title AS book_title, "
        + "a.name AS author_name, "
        + "l.id AS loan_id "
        + "FROM books b "
        + "JOIN authors a ON a.id = b.author_id "
        + "LEFT JOIN loan_records l ON l.book_id = b.id AND l.return_date IS NULL "
        + "ORDER BY b.created_at DESC, b.id DESC;";

    return template.query(sql, BOOK_STATUS_ROW_MAPPER);
  }

  /**
   * 識別子で蔵書を取得する。
   *
   * @param id 書籍の識別子
   * @return 該当する蔵書。存在しない場合は空
   */
  @Override
  public Optional<Book> findById(Integer id) {
    String sql = "SELECT "
        + "b.id AS book_id, "
        + "b.title AS book_title, "
        + "a.name AS author_name "
        + "FROM books b "
        + "JOIN authors a ON a.id = b.author_id "
        + "WHERE b.id = :id;";
    SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);

    List<Book> result = template.query(sql, param, BOOK_ROW_MAPPER);
    return result.isEmpty() ? Optional.empty() : Optional.of(result.get(0));
  }

  /**
   * タイトルで蔵書を貸出状態付きで取得する。
   *
   * @param title 書籍のタイトル
   * @return 該当する蔵書と貸出状態の一覧。該当がない場合は空の一覧
   */
  @Override
  public List<BookStatus> findByTitle(String title) {
    String sql = "SELECT "
        + "b.id AS book_id, "
        + "b.title AS book_title, "
        + "a.name AS author_name, "
        + "l.id AS loan_id "
        + "FROM books b "
        + "JOIN authors a ON a.id = b.author_id "
        + "LEFT JOIN loan_records l ON l.book_id = b.id AND l.return_date IS NULL "
        + "WHERE b.title LIKE :title "
        + "ORDER BY b.created_at DESC, b.id DESC;";
    SqlParameterSource param = new MapSqlParameterSource().addValue("title", "%" + title + "%");

    return template.query(sql, param, BOOK_STATUS_ROW_MAPPER);
  }

  /**
   * 著者名で著者を取得する。
   *
   * @param name 著者名
   * @return 該当する著者。存在しない場合は空
   */
  private Optional<Author> findByAuthorName(String name) {
    String sql = "SELECT id, name FROM authors WHERE name = :name;";
    SqlParameterSource param = new MapSqlParameterSource().addValue("name", name);
    List<Author> result = template.query(sql, param, AUTHOR_ROW_MAPPER);

    return result.isEmpty() ? Optional.empty() : Optional.of(result.get(0));
  }

  /**
   * 著者を登録し、採番された識別子を返す。
   *
   * @param author 登録する著者
   * @return 登録した著者の識別子
   */
  private int createAuthorReturnId(Author author) {
    String sql = "INSERT INTO authors (name) VALUES (:name) RETURNING id;";
    SqlParameterSource param = new MapSqlParameterSource().addValue("name", author.getName());
    return template.queryForObject(sql, param, Integer.class);
  }
}
