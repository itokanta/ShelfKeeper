package com.example.shelfkeeper.usecase.loanrecord.checkin;

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
import com.example.shelfkeeper.domain.loanrecord.LoanRecord;
import com.example.shelfkeeper.usecase.port.LoanRecordRepository;

/**
 * {@link LoanRecordCheckInInteractor} のテストクラス。
 *
 * @author itokanta
 */
@ExtendWith(MockitoExtension.class)
public class LoanRecordCheckInInteractorTest {

  @Mock
  private LoanRecordRepository loanRecordRepository;

  @InjectMocks
  private LoanRecordCheckInInteractor loanRecordCheckInInteractor;

  /**
   * 未返却の貸出記録を返却できることを検証する。
   */
  @Test
  void handleSuccess() {
    LoanRecordCheckInInputData inInputData = new LoanRecordCheckInInputData(1);
    LoanRecord findByIdResult = new LoanRecord(1, 2, 3, LocalDate.of(2026, 8, 20), LocalDate.of(2026, 8, 26), null);

    when(loanRecordRepository.findById(inInputData.getId())).thenReturn(Optional.of(findByIdResult));

    loanRecordCheckInInteractor.handle(inInputData);

    verify(loanRecordRepository).findById(inInputData.getId());
    ArgumentCaptor<LoanRecord> captor = ArgumentCaptor.forClass(LoanRecord.class);
    verify(loanRecordRepository).checkIn(captor.capture());

    LoanRecord newData = captor.getValue();
    assertEquals(findByIdResult.getId(), newData.getId());
    assertEquals(findByIdResult.getUserId(), newData.getUserId());
    assertEquals(findByIdResult.getBookId(), newData.getBookId());
    assertEquals(findByIdResult.getLoanDate(), newData.getLoanDate());
    assertEquals(findByIdResult.getDueDate(), newData.getDueDate());
    assertEquals(LocalDate.now(), newData.getReturnDate());
  }

  /**
   * 貸出記録が存在しない場合、例外をスローし返却しないことを検証する。
   */
  @Test
  void handleNoFoundError() {
    LoanRecordCheckInInputData inInputData = new LoanRecordCheckInInputData(1);

    when(loanRecordRepository.findById(inInputData.getId())).thenReturn(Optional.empty());

    assertThrows(BadRequestException.class, () -> loanRecordCheckInInteractor.handle(inInputData));
    verify(loanRecordRepository).findById(inInputData.getId());
    verify(loanRecordRepository, never()).checkIn(any());
  }

  /**
   * すでに返却済みの場合、例外をスローし返却しないことを検証する。
   */
  @Test
  void handleReturnedError() {
    LoanRecordCheckInInputData inInputData = new LoanRecordCheckInInputData(1);
    LoanRecord findByIdResult = new LoanRecord(1, 2, 3, LocalDate.of(2026, 8, 20), LocalDate.of(2026, 8, 26), LocalDate.of(2026, 8, 25));

    when(loanRecordRepository.findById(inInputData.getId())).thenReturn(Optional.of(findByIdResult));

    assertThrows(BadRequestException.class, () -> loanRecordCheckInInteractor.handle(inInputData));
    verify(loanRecordRepository).findById(inInputData.getId());
    verify(loanRecordRepository, never()).checkIn(any());
  }
}
