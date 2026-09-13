package com.example.shelfkeeper.usecase.loanrecord.checkin;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.shelfkeeper.adapter.web.errorresponse.BadRequestException;
import com.example.shelfkeeper.domain.loanrecord.LoanRecord;
import com.example.shelfkeeper.usecase.port.LoanRecordRepository;

/**
 * {@link LoanRecordCheckInUseCase} の実装クラス。
 * 指定された貸出記録が存在し、未返却であることを確認したうえで返却日を登録する。
 *
 * @author itokanta
 */
@Service
public class LoanRecordCheckInInteractor implements LoanRecordCheckInUseCase {
  /** 貸出記録の永続化を担うリポジトリ。 */
  private final LoanRecordRepository loanRecordRepository;

  public LoanRecordCheckInInteractor(LoanRecordRepository loanRecordRepository) {
    this.loanRecordRepository = loanRecordRepository;
  }

  /**
   * 貸出記録を返却する。
   * 指定された貸出記録が存在しない場合、またはすでに返却済みの場合は例外をスローする。
   *
   * @param loanRecordCheckInInputData 返却する貸出記録の入力データ
   * @throws BadRequestException 指定された貸出記録が存在しない場合、またはすでに返却済みの場合
   */
  @Override
  public void handle(LoanRecordCheckInInputData loanRecordCheckInInputData) {
    Optional<LoanRecord> findByIdResult = loanRecordRepository.findById(loanRecordCheckInInputData.getId());

    if (findByIdResult.isEmpty()) {
      throw new BadRequestException("指定された貸出記録は存在しません");
    }

    LoanRecord checkInTarget = findByIdResult.get();

    if (checkInTarget.getReturnDate() != null) {
      throw new BadRequestException("指定された貸出記録はすでに返却済みです");
    }

    loanRecordRepository.checkIn(new LoanRecord(checkInTarget.getId(), checkInTarget.getUserId(),
        checkInTarget.getBookId(), checkInTarget.getLoanDate(), checkInTarget.getDueDate(), LocalDate.now()));
  }
}
