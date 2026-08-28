package com.example.shelfkeeper.usecase.loanrecord.bookhistory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.shelfkeeper.usecase.port.LoanRecordRepository;
import com.example.shelfkeeper.usecase.port.entity.loanrecord.BookHistory;

/**
 * {@link LoanRecordBookHistoryInteractor} のテストクラス。
 *
 * @author itokanta
 */
@ExtendWith(MockitoExtension.class)
public class LoanRecordBookHistoryInteractorTest {

  @Mock
  private LoanRecordRepository loanRecordRepository;

  @Mock
  private LoanRecordBookHistoryBoundary loanRecordBookHistoryBoundary;

  @InjectMocks
  private LoanRecordBookHistoryInteractor loanRecordBookHistoryInteractor;

  /**
   * 指定した書籍の貸出履歴を取得し、出力境界へ引き渡すことを検証する。
   */
  @Test
  void handleSuccess() {
    LoanRecordBookHistoryInputData targetBookId = new LoanRecordBookHistoryInputData(1);
    List<BookHistory> findByBookIdResult = new ArrayList<>();
    findByBookIdResult.add(new BookHistory(1, "test1", "bookName", LocalDate.of(2026, 8, 21), LocalDate.of(2026, 8, 26), null));
    findByBookIdResult.add(new BookHistory(2, "test2", "bookName", LocalDate.of(2026, 8, 15), LocalDate.of(2026, 8, 21), LocalDate.of(2026, 8, 20)));

    when(loanRecordRepository.findByBookId(targetBookId.getBookId())).thenReturn(findByBookIdResult);

    loanRecordBookHistoryInteractor.handle(targetBookId);

    verify(loanRecordRepository).findByBookId(targetBookId.getBookId());
    ArgumentCaptor<LoanRecordBookHistoryOutputData> captor = ArgumentCaptor.forClass(LoanRecordBookHistoryOutputData.class);
    verify(loanRecordBookHistoryBoundary).complete(captor.capture());

    LoanRecordBookHistoryOutputData outputData = captor.getValue();
    List<LoanRecordBookHistoryItemOutputData> itemList = outputData.getOutputData();
    assertEquals(2, itemList.size());
    for(int i = 0; i < itemList.size(); i++) {
      BookHistory bookHistory = findByBookIdResult.get(i);
      LoanRecordBookHistoryItemOutputData item = itemList.get(i);
      assertEquals(bookHistory.getLoanRecordId(), item.getLoanRecordId());
      assertEquals(bookHistory.getBookName(), item.getBookName());
      assertEquals(bookHistory.getUserName(), item.getUserName());
      assertEquals(bookHistory.getLoanDate(), item.getLoanDate());
      assertEquals(bookHistory.getDueDate(), item.getDueDate());
      assertEquals(bookHistory.getReturnDate(), item.getReturnDate());
    }
  }
}
