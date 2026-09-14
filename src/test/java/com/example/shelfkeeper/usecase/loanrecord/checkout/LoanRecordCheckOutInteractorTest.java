package com.example.shelfkeeper.usecase.loanrecord.checkout;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.shelfkeeper.adapter.web.errorresponse.BadRequestException;
import com.example.shelfkeeper.domain.book.Book;
import com.example.shelfkeeper.domain.loanrecord.LoanRecord;
import com.example.shelfkeeper.domain.user.User;
import com.example.shelfkeeper.usecase.port.BookRepository;
import com.example.shelfkeeper.usecase.port.LoanRecordRepository;
import com.example.shelfkeeper.usecase.port.UserRepository;

/**
 * {@link LoanRecordCheckOutInteractor} のテストクラス。
 *
 * @author itokanta
 */
@ExtendWith(MockitoExtension.class)
public class LoanRecordCheckOutInteractorTest {

  @Mock
  private LoanRecordRepository loanRecordRepository;

  @Mock
  private BookRepository bookRepository;

  @Mock
  private UserRepository userRepository;

  @InjectMocks
  private LoanRecordCheckOutInteractor loanRecordCheckOutInteractor;

  /**
   * 利用者・蔵書が存在し未貸出の場合に貸出記録を登録できることを検証する。
   */
  @Test
  void handleSuccess() {
    LoanRecordCheckOutInputData inputData = new LoanRecordCheckOutInputData(1, 2);
    User findByUserIdResult = new User(1, "testUser");
    Book findByBookIdResult = new Book(2, "test", "testName");

    when(loanRecordRepository.returnDateIsNullFindByBookId(inputData.getBookId())).thenReturn(Optional.empty());
    when(userRepository.findById(inputData.getUserId())).thenReturn(Optional.of(findByUserIdResult));
    when(bookRepository.findById(inputData.getBookId())).thenReturn(Optional.of(findByBookIdResult));

    loanRecordCheckOutInteractor.handle(inputData);

    verify(loanRecordRepository).returnDateIsNullFindByBookId(inputData.getBookId());
    verify(userRepository).findById(inputData.getUserId());
    verify(bookRepository).findById(inputData.getBookId());
    ArgumentCaptor<LoanRecord> captor = ArgumentCaptor.forClass(LoanRecord.class);
    verify(loanRecordRepository).checkOut(captor.capture());

    LoanRecord created = captor.getValue();
    assertEquals(inputData.getUserId(), created.getUserId());
    assertEquals(inputData.getBookId(), created.getBookId());
    assertEquals(LocalDate.now(), created.getLoanDate());
    assertEquals(LocalDate.now().plusDays(7), created.getDueDate());
    assertEquals(null, created.getReturnDate());
  }

  /**
   * すでに貸出中の書籍の場合、例外をスローし登録しないことを検証する。
   */
  @Test
  void handleLoanedError() {
    LoanRecordCheckOutInputData inputData = new LoanRecordCheckOutInputData(1, 2);
    LoanRecord loanedFindResult = new LoanRecord(3, 4, 2, LocalDate.of(2026, 8, 20), LocalDate.of(2026, 8, 26), null);
    User findByUserIdResult = new User(1, "testUser");
    Book findByBookIdResult = new Book(2, "test", "testName");

    when(loanRecordRepository.returnDateIsNullFindByBookId(inputData.getBookId()))
        .thenReturn(Optional.of(loanedFindResult));
    when(userRepository.findById(inputData.getUserId())).thenReturn(Optional.of(findByUserIdResult));
    when(bookRepository.findById(inputData.getBookId())).thenReturn(Optional.of(findByBookIdResult));

    assertThrows(BadRequestException.class, () -> loanRecordCheckOutInteractor.handle(inputData));

    verify(loanRecordRepository).returnDateIsNullFindByBookId(inputData.getBookId());
    verify(userRepository).findById(inputData.getUserId());
    verify(bookRepository).findById(inputData.getBookId());
    verify(loanRecordRepository, never()).checkOut(any());
  }

  /**
   * 利用者が存在しない場合、例外をスローし登録しないことを検証する。
   */
  @Test
  void handleUserNotFoundError() {
    LoanRecordCheckOutInputData inputData = new LoanRecordCheckOutInputData(1, 2);
    Book findByBookIdResult = new Book(2, "test", "testName");

    when(loanRecordRepository.returnDateIsNullFindByBookId(inputData.getBookId())).thenReturn(Optional.empty());
    when(userRepository.findById(inputData.getUserId())).thenReturn(Optional.empty());
    when(bookRepository.findById(inputData.getBookId())).thenReturn(Optional.of(findByBookIdResult));

    assertThrows(BadRequestException.class, () -> loanRecordCheckOutInteractor.handle(inputData));

    verify(loanRecordRepository).returnDateIsNullFindByBookId(inputData.getBookId());
    verify(userRepository).findById(inputData.getUserId());
    verify(bookRepository).findById(inputData.getBookId());
    verify(loanRecordRepository, never()).checkOut(any());
  }

  /**
   * 蔵書が存在しない場合、例外をスローし登録しないことを検証する。
   */
  @Test
  void handleBookNotFoundError() {
    LoanRecordCheckOutInputData inputData = new LoanRecordCheckOutInputData(1, 2);
    User findByUserIdResult = new User(1, "testUser");

    when(loanRecordRepository.returnDateIsNullFindByBookId(inputData.getBookId())).thenReturn(Optional.empty());
    when(userRepository.findById(inputData.getUserId())).thenReturn(Optional.of(findByUserIdResult));
    when(bookRepository.findById(inputData.getBookId())).thenReturn(Optional.empty());

    assertThrows(BadRequestException.class, () -> loanRecordCheckOutInteractor.handle(inputData));

    verify(loanRecordRepository).returnDateIsNullFindByBookId(inputData.getBookId());
    verify(userRepository).findById(inputData.getUserId());
    verify(bookRepository).findById(inputData.getBookId());
    verify(loanRecordRepository, never()).checkOut(any());
  }
}
