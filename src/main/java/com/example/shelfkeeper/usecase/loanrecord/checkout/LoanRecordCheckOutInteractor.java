package com.example.shelfkeeper.usecase.loanrecord.checkout;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.shelfkeeper.domain.book.Book;
import com.example.shelfkeeper.domain.loanrecord.LoanRecord;
import com.example.shelfkeeper.domain.user.User;
import com.example.shelfkeeper.usecase.port.BookRepository;
import com.example.shelfkeeper.usecase.port.LoanRecordRepository;
import com.example.shelfkeeper.usecase.port.UserRepository;

/**
 * {@link LoanRecordCheckOutUseCase} の実装クラス。
 * 利用者・蔵書の存在と未返却貸出の有無を確認したうえで、貸出記録を登録する。
 *
 * @author itokanta
 */
@Service
public class LoanRecordCheckOutInteractor implements LoanRecordCheckOutUseCase{
  /** 貸出記録の永続化を担うリポジトリ。 */
  private final LoanRecordRepository loanRecordRepository;
  /** 蔵書の永続化を担うリポジトリ。 */
  private final BookRepository bookRepository;
  /** 利用者の永続化を担うリポジトリ。 */
  private final UserRepository userRepository;

  public LoanRecordCheckOutInteractor(LoanRecordRepository loanRecordRepository, BookRepository bookRepository, UserRepository userRepository) {
    this.loanRecordRepository = loanRecordRepository;
    this.bookRepository = bookRepository;
    this.userRepository = userRepository;
  }

  /**
   * 貸出記録を登録する。
   * すでに貸出中の書籍、未登録の利用者、または未登録の書籍の場合は例外をスローする。
   *
   * @param loanRecordCheckOutInputData 登録する貸出記録の入力データ
   * @throws RuntimeException すでに貸出中の場合、利用者が存在しない場合、または蔵書が存在しない場合
   */
  @Override
  public void handle(LoanRecordCheckOutInputData loanRecordCheckOutInputData) {
    Optional<LoanRecord> loanedCheck = loanRecordRepository.returnDateIsNullFindByBookId(loanRecordCheckOutInputData.getBookId());
    Optional<User> findByUserIdResult = userRepository.findById(loanRecordCheckOutInputData.getUserId());
    Optional<Book> findByBookIdResult = bookRepository.findById(loanRecordCheckOutInputData.getBookId());
    
    if(!loanedCheck.isEmpty()) {
      throw new RuntimeException("該当の書籍はすでに貸出中です");
    }

    if(findByUserIdResult.isEmpty()) {
      throw new RuntimeException("指定されたユーザーは登録されていません");
    }

    if(findByBookIdResult.isEmpty()) {
      throw new RuntimeException("指定された書籍は登録されていません");
    }

    LoanRecord loan = LoanRecord.checkOut(loanRecordCheckOutInputData.getUserId(), loanRecordCheckOutInputData.getBookId());

    loanRecordRepository.checkOut(loan);
  }
}
