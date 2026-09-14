package com.example.shelfkeeper.infrastructure.loanrecord;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.shelfkeeper.domain.loanrecord.LoanRecord;
import com.example.shelfkeeper.usecase.port.LoanRecordRepository;
import com.example.shelfkeeper.usecase.port.entity.loanrecord.BookHistory;

/**
 * {@link LoanRecordRepository} の JDBC 実装クラス。
 *
 * @author itokanta
 */
@Repository
public class LoanRecordJdbcRepository implements LoanRecordRepository {
  /** JDBC テンプレート。 */
  private final NamedParameterJdbcTemplate template;

  /** 貸出記録の行マッパー。 */
  private static final RowMapper<LoanRecord> LOAN_ROW_MAPPER = (rs, i) -> {
    LocalDate returnDate = rs.getObject("return_date", LocalDate.class);
    LoanRecord loanRecord = new LoanRecord(
        rs.getInt("id"),
        rs.getInt("user_id"),
        rs.getInt("book_id"),
        rs.getObject("loan_date", LocalDate.class),
        rs.getObject("due_date", LocalDate.class),
        returnDate != null ? returnDate : null);
    return loanRecord;
  };

  /** 書籍の貸出履歴の行マッパー。 */
  private static final RowMapper<BookHistory> BOOK_HISTORY_ROW_MAPPER = (rs, i) -> {
    BookHistory bookHistory = new BookHistory(
        rs.getInt("loan_id"),
        rs.getString("user_name"),
        rs.getString("book_title"),
        rs.getObject("loan_date", LocalDate.class),
        rs.getObject("due_date", LocalDate.class),
        rs.getObject("return_date", LocalDate.class));
    return bookHistory;
  };

  public LoanRecordJdbcRepository(NamedParameterJdbcTemplate template) {
    this.template = template;
  }

  /**
   * 貸出記録を返却する。
   *
   * @param loanRecord 返却する貸出記録
   */
  @Override
  public void checkIn(LoanRecord loanRecord) {
    String sql = "UPDATE loan_records SET return_date = :returnDate WHERE id = :id;";
    SqlParameterSource param = new MapSqlParameterSource()
        .addValue("returnDate", loanRecord.getReturnDate())
        .addValue("id", loanRecord.getId());
    template.update(sql, param);
  }

  /**
   * 貸出を登録する。
   *
   * @param loanRecord 登録する貸出記録
   */
  @Override
  public void checkOut(LoanRecord loanRecord) {
    String sql = "INSERT INTO loan_records (user_id, book_id, loan_date, due_date, return_date) "
        + "VALUES (:userId, :bookId, :loanDate, :dueDate, :returnDate);";
    SqlParameterSource param = new MapSqlParameterSource()
        .addValue("userId", loanRecord.getUserId())
        .addValue("bookId", loanRecord.getBookId())
        .addValue("loanDate", loanRecord.getLoanDate())
        .addValue("dueDate", loanRecord.getDueDate())
        .addValue("returnDate", null);

    template.update(sql, param);
  }

  /**
   * 書籍の識別子で貸出履歴を取得する。
   *
   * @param bookId 書籍の識別子
   * @return 該当する貸出履歴の一覧
   */
  @Override
  public List<BookHistory> findByBookId(Integer bookId) {
    String sql = "SELECT "
        + "l.id AS loan_id, "
        + "u.name AS user_name, "
        + "b.title AS book_title, "
        + "l.loan_date AS loan_date, "
        + "l.due_date AS due_date, "
        + "l.return_date AS return_date "
        + "FROM loan_records l "
        + "JOIN users u ON l.user_id = u.id "
        + "JOIN books b ON l.book_id = b.id "
        + "WHERE l.book_id = :bookId "
        + "ORDER BY l.loan_date DESC, l.id DESC;";

    SqlParameterSource param = new MapSqlParameterSource().addValue("bookId", bookId);
    return template.query(sql, param, BOOK_HISTORY_ROW_MAPPER);
  }

  /**
   * 識別子で貸出記録を取得する。
   *
   * @param id 貸出記録の識別子
   * @return 該当する貸出記録。存在しない場合は空
   */
  @Override
  public Optional<LoanRecord> findById(Integer id) {
    String sql = "SELECT id, user_id, book_id, loan_date, due_date, return_date FROM loan_records WHERE id = :id;";
    SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);

    List<LoanRecord> result = template.query(sql, param, LOAN_ROW_MAPPER);
    return result.isEmpty() ? Optional.empty() : Optional.of(result.get(0));
  }

  /**
   * 書籍の識別子で未返却の貸出記録を取得する。
   *
   * @param bookId 書籍の識別子
   * @return 未返却の貸出記録。存在しない場合は空
   */
  @Override
  public Optional<LoanRecord> returnDateIsNullFindByBookId(Integer bookId) {
    String sql = "SELECT id, user_id, book_id, loan_date, due_date, return_date FROM loan_records WHERE book_id = :bookId AND return_date IS NULL;";
    SqlParameterSource param = new MapSqlParameterSource().addValue("bookId", bookId);

    List<LoanRecord> result = template.query(sql, param, LOAN_ROW_MAPPER);
    return result.isEmpty() ? Optional.empty() : Optional.of(result.get(0));
  }
}
