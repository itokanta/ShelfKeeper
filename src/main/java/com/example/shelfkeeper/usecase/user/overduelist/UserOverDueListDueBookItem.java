package com.example.shelfkeeper.usecase.user.overduelist;

import java.time.LocalDate;

/**
 * 延滞利用者一覧の延滞書籍1件分の出力データ。
 *
 * @author itokanta
 */
public class UserOverDueListDueBookItem {
  /** 書籍の識別子。 */
  private final Integer bookId;
  /** 書籍のタイトル。 */
  private final String bookTitle;
  /** 貸出日。 */
  private final LocalDate loanDate;
  /** 返却期限日。 */
  private final LocalDate dueDate;

  public UserOverDueListDueBookItem(Integer bookId, String bookTitle, LocalDate loanDate, LocalDate dueDate) {
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
