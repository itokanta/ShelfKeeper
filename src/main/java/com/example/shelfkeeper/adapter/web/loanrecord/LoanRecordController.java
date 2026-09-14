package com.example.shelfkeeper.adapter.web.loanrecord;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.shelfkeeper.adapter.web.loanrecord.bookhistory.LoanRecordBookHistoryPresenter;
import com.example.shelfkeeper.adapter.web.loanrecord.bookhistory.LoanRecordBookHistoryRequest;
import com.example.shelfkeeper.adapter.web.loanrecord.bookhistory.LoanRecordBookHistoryResponse;
import com.example.shelfkeeper.adapter.web.loanrecord.checkout.LoanRecordCheckOutRequest;
import com.example.shelfkeeper.usecase.loanrecord.bookhistory.LoanRecordBookHistoryUseCase;
import com.example.shelfkeeper.usecase.loanrecord.checkin.LoanRecordCheckInInputData;
import com.example.shelfkeeper.usecase.loanrecord.checkin.LoanRecordCheckInUseCase;
import com.example.shelfkeeper.usecase.loanrecord.checkout.LoanRecordCheckOutUseCase;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;

/**
 * 貸出記録に関する API を提供するコントローラー。
 *
 * @author itokanta
 */
@RestController
@RequestMapping("loanrecords")
@CrossOrigin(origins = "http://localhost:8080", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class LoanRecordController {
  /** 書籍の貸出履歴取得ユースケース。 */
  private final LoanRecordBookHistoryUseCase loanRecordBookHistoryUseCase;
  /** 書籍の貸出履歴取得のプレゼンター。 */
  private final LoanRecordBookHistoryPresenter loanRecordBookHistoryPresenter;
  /** 貸出記録登録ユースケース。 */
  private final LoanRecordCheckOutUseCase loanRecordCheckOutUseCase;
  /** 貸出記録返却ユースケース。 */
  private final LoanRecordCheckInUseCase loanRecordCheckInUseCase;

  public LoanRecordController(LoanRecordBookHistoryUseCase loanRecordBookHistoryUseCase,
      LoanRecordBookHistoryPresenter loanRecordBookHistoryPresenter,
      LoanRecordCheckOutUseCase loanRecordCheckOutUseCase, LoanRecordCheckInUseCase loanRecordCheckInUseCase) {
    this.loanRecordBookHistoryUseCase = loanRecordBookHistoryUseCase;
    this.loanRecordBookHistoryPresenter = loanRecordBookHistoryPresenter;
    this.loanRecordCheckOutUseCase = loanRecordCheckOutUseCase;
    this.loanRecordCheckInUseCase = loanRecordCheckInUseCase;
  }

  /**
   * 書籍の貸出履歴を取得する。
   *
   * @param request 履歴取得リクエスト
   * @return 貸出履歴一覧
   */
  @GetMapping("/bookhistory")
  public ResponseEntity<LoanRecordBookHistoryResponse> bookHistory(@ModelAttribute @Valid LoanRecordBookHistoryRequest request) {
    loanRecordBookHistoryUseCase.handle(request.toLoanRecordBookHistoryInputData());
    return ResponseEntity.ok(loanRecordBookHistoryPresenter.getResponse());
  }

  /**
   * 貸出記録を登録する。
   *
   * @param request 貸出リクエスト
   * @return 登録成功時は 201 Created
   */
  @PostMapping("")
  public ResponseEntity<Void> checkOut(@RequestBody @Valid LoanRecordCheckOutRequest request) {
    loanRecordCheckOutUseCase.handle(request.toLoanRecordCheckOutInputData());
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  /**
   * 貸出記録を返却する。
   *
   * @param id 返却対象の貸出記録の識別子
   * @return 返却成功時は 204 No Content
   */
  @PutMapping("/{id}")
  public ResponseEntity<Void> checkIn(@PathVariable @Min(value = 1, message = "貸出記録IDは1以上で指定してください") Integer id) {
    loanRecordCheckInUseCase.handle(new LoanRecordCheckInInputData(id));
    return ResponseEntity.noContent().build();
  }
}
