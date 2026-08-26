package com.example.shelfkeeper.infrastructure.entity.book;

import java.time.LocalDate;

/**
 * 延滞書籍を表すエンティティ。
 *
 * @author itokanta
 */
public class UserOverDueBook {
  /** 書籍の識別子。 */
  private final Integer bookId;
  /** 書籍のタイトル。 */
  private final String bookTitle;
  /** 貸出日。 */
  private final LocalDate loanDate;
  /** 返却期限日。 */
  private final LocalDate dueDate;

  public UserOverDueBook(Integer bookId, String bookTitle, LocalDate loanDate, LocalDate dueDate) {
    this.bookId = bookId;
    this.bookTitle = bookTitle;
    this.loanDate = loanDate;
    this.dueDate = dueDate;
  }

  public Integer getBookId() {
    return bookId;
  }

  public String getBookTitle() {
    return bookTitle;
  }

  public LocalDate getLoanDate() {
    return loanDate;
  }

  public LocalDate getDueDate() {
    return dueDate;
  }
}
