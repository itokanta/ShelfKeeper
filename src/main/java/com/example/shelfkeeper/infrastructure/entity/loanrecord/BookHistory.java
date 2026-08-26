package com.example.shelfkeeper.infrastructure.entity.loanrecord;

import java.time.LocalDate;

/**
 * 書籍の貸出履歴を表すエンティティ。
 *
 * @author itokanta
 */
public class BookHistory {
  /** 貸出記録の識別子。 */
  private final Integer loanRecordId;
  /** 利用者名。 */
  private final String userName;
  /** 書籍名。 */
  private final String bookName;
  /** 貸出日。 */
  private final LocalDate loanDate;
  /** 返却期限日。 */
  private final LocalDate dueDate;
  /** 返却日。未返却の場合は {@code null}。 */
  private final LocalDate returnDate;

  public BookHistory(Integer loanRecordId, String userName, String bookName, LocalDate loanDate, LocalDate dueDate,
      LocalDate returnDate) {
    this.loanRecordId = loanRecordId;
    this.userName = userName;
    this.bookName = bookName;
    this.loanDate = loanDate;
    this.dueDate = dueDate;
    this.returnDate = returnDate;
  }

  public Integer getLoanRecordId() {
    return loanRecordId;
  }

  public String getUserName() {
    return userName;
  }

  public String getBookName() {
    return bookName;
  }

  public LocalDate getLoanDate() {
    return loanDate;
  }

  public LocalDate getDueDate() {
    return dueDate;
  }

  public LocalDate getReturnDate() {
    return returnDate;
  }
}
