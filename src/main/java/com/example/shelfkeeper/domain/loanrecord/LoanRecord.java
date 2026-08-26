package com.example.shelfkeeper.domain.loanrecord;

import java.time.LocalDate;

/**
 * 貸出記録を表すドメインクラス。
 *
 * @author itokanta
 */
public class LoanRecord {
  /** 貸出記録の識別子。 */
  private final Integer id;
  /** 貸出利用者の識別子。 */
  private final Integer userId;
  /** 貸出書籍の識別子。 */
  private final Integer bookId;
  /** 貸出日。 */
  private final LocalDate loanDate;
  /** 返却期限日。 */
  private final LocalDate dueDate;
  /** 返却日。未返却の場合は {@code null}。 */
  private final LocalDate returnDate;
  
  public LoanRecord(Integer id, Integer userId, Integer bookId, LocalDate loanDate, LocalDate dueDate,LocalDate returnDate) {
    this.id = id;
    this.userId = userId;
    this.bookId = bookId;
    this.loanDate = loanDate;
    this.dueDate = dueDate;
    this.returnDate = returnDate;
  }

	public Integer getId() {
		return id;
	}

	public Integer getUserId() {
		return userId;
	}

	public Integer getBookId() {
		return bookId;
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
