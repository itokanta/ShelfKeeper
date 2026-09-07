package com.example.shelfkeeper.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.example.shelfkeeper.adapter.web.loanrecord.LoanRecordController;
import com.example.shelfkeeper.adapter.web.loanrecord.bookhistory.LoanRecordBookHistoryItem;
import com.example.shelfkeeper.adapter.web.loanrecord.bookhistory.LoanRecordBookHistoryPresenter;
import com.example.shelfkeeper.adapter.web.loanrecord.bookhistory.LoanRecordBookHistoryResponse;
import com.example.shelfkeeper.usecase.loanrecord.bookhistory.LoanRecordBookHistoryInputData;
import com.example.shelfkeeper.usecase.loanrecord.bookhistory.LoanRecordBookHistoryUseCase;

/**
 * {@link LoanRecordController} のテストクラス。
 *
 * @author itokanta
 */
@WebMvcTest(LoanRecordController.class)
@AutoConfigureMockMvc(addFilters = false)
public class LoanRecordControllerTest {
  private final MockMvc mockMvc;

  @MockitoBean
  private LoanRecordBookHistoryUseCase loanRecordBookHistoryUseCase;

  @MockitoBean
  private LoanRecordBookHistoryPresenter loanRecordBookHistoryPresenter;

  @Autowired
  public LoanRecordControllerTest(MockMvc mockMvc) {
    this.mockMvc = mockMvc;
  }

  /**
   * 書籍の貸出履歴取得に成功することを検証する。
   */
  @Test
  void bookHistorySuccess() throws Exception {
    List<LoanRecordBookHistoryItem> responseItem = List.of(new LoanRecordBookHistoryItem(
      1,
      "testUserName",
      "testBookName",
      LocalDate.of(2026, 8, 25),
      LocalDate.of(2026, 8, 29),
      LocalDate.of(2026, 8, 28)
    ));
    LoanRecordBookHistoryResponse response = new LoanRecordBookHistoryResponse(responseItem);

    when(loanRecordBookHistoryPresenter.getResponse()).thenReturn(response);

    mockMvc.perform(get("/loanrecords/bookhistory")
            .param("bookId", "1"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.loanRecordBookHistoryList[0].loanRecordId").value(1))
        .andExpect(jsonPath("$.loanRecordBookHistoryList[0].userName").value("testUserName"))
        .andExpect(jsonPath("$.loanRecordBookHistoryList[0].bookName").value("testBookName"))
        .andExpect(jsonPath("$.loanRecordBookHistoryList[0].loanDate").value("2026-08-25"))
        .andExpect(jsonPath("$.loanRecordBookHistoryList[0].dueDate").value("2026-08-29"))
        .andExpect(jsonPath("$.loanRecordBookHistoryList[0].returnDate").value("2026-08-28"));

    ArgumentCaptor<LoanRecordBookHistoryInputData> captor = ArgumentCaptor.forClass(LoanRecordBookHistoryInputData.class);
    verify(loanRecordBookHistoryUseCase).handle(captor.capture());

    assertEquals(1, captor.getValue().getBookId());
  }

  /**
   * 書籍IDのバリデーションエラーの場合、履歴取得ユースケースを呼び出さないことを検証する。
   */
  @Test
  void bookHistoryValidationError() throws Exception {
    mockMvc.perform(get("/loanrecords/bookhistory")
            .param("bookId", ""))
        .andExpect(status().isBadRequest());

    verify(loanRecordBookHistoryUseCase, never()).handle(any());
  }
}
