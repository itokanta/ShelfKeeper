package com.example.shelfkeeper.adapter.web.user.overduelist;

import java.time.LocalDate;

/**
 * 延滞中の書籍の1件分のレスポンス項目。
 *
 * @author itokanta
 */
public class DueBookListItem {
  /** 書籍の識別子。 */
  private final Integer bookId;
  /** 書籍のタイトル。 */
  private final String bookTitle;
  /** 貸出日。 */
  private final LocalDate loanDate;
  /** 返却期限日。 */
  private final LocalDate dueDate;

  public DueBookListItem(Integer bookId, String bookTitle, LocalDate loanDate, LocalDate dueDate) {
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
