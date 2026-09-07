package com.example.shelfkeeper.adapter.web.loanrecord;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.shelfkeeper.adapter.web.loanrecord.bookhistory.LoanRecordBookHistoryPresenter;
import com.example.shelfkeeper.adapter.web.loanrecord.bookhistory.LoanRecordBookHistoryRequest;
import com.example.shelfkeeper.adapter.web.loanrecord.bookhistory.LoanRecordBookHistoryResponse;
import com.example.shelfkeeper.usecase.loanrecord.bookhistory.LoanRecordBookHistoryUseCase;

import jakarta.validation.Valid;

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

  public LoanRecordController(LoanRecordBookHistoryUseCase loanRecordBookHistoryUseCase,
      LoanRecordBookHistoryPresenter loanRecordBookHistoryPresenter) {
    this.loanRecordBookHistoryUseCase = loanRecordBookHistoryUseCase;
    this.loanRecordBookHistoryPresenter = loanRecordBookHistoryPresenter;
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
}
