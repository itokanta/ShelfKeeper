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
  /** 貸出期間（日数）。 */
  private static final int LOAN_PERIOD_DAYS = 7;
  
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

    /**
     * 新規貸出記録を作成する。
     * 貸出日は本日、返却期限日は本日＋貸出期間、返却日は未設定とする。
     *
     * @param userId 貸出利用者の識別子
     * @param bookId 貸出書籍の識別子
     * @return 作成した貸出記録
     */
    public static LoanRecord checkOut(Integer userId, Integer bookId) {
      return new LoanRecord(null, userId, bookId, LocalDate.now(), LocalDate.now().plusDays(LOAN_PERIOD_DAYS), null);
    }
}
