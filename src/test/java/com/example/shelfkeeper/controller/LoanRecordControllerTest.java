package com.example.shelfkeeper.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.example.shelfkeeper.adapter.web.loanrecord.LoanRecordController;
import com.example.shelfkeeper.adapter.web.loanrecord.bookhistory.LoanRecordBookHistoryItem;
import com.example.shelfkeeper.adapter.web.loanrecord.bookhistory.LoanRecordBookHistoryPresenter;
import com.example.shelfkeeper.adapter.web.loanrecord.bookhistory.LoanRecordBookHistoryResponse;
import com.example.shelfkeeper.usecase.loanrecord.bookhistory.LoanRecordBookHistoryInputData;
import com.example.shelfkeeper.usecase.loanrecord.bookhistory.LoanRecordBookHistoryUseCase;
import com.example.shelfkeeper.usecase.loanrecord.checkout.LoanRecordCheckOutInputData;
import com.example.shelfkeeper.usecase.loanrecord.checkout.LoanRecordCheckOutUseCase;

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

  @MockitoBean
  private LoanRecordCheckOutUseCase loanRecordCheckOutUseCase;

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
            .param("bookId", "0"))
        .andExpect(status().isBadRequest());

    verify(loanRecordBookHistoryUseCase, never()).handle(any());
  }

  /**
   * 貸出記録の登録に成功することを検証する。
   */
  @Test
  void checkOutSuccess() throws Exception {
    mockMvc.perform(post("/loanrecords")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"userId\":\"1\",\"bookId\":\"2\"}"))
        .andExpect(status().isCreated());

    ArgumentCaptor<LoanRecordCheckOutInputData> captor = ArgumentCaptor.forClass(LoanRecordCheckOutInputData.class);
    verify(loanRecordCheckOutUseCase).handle(captor.capture());

    LoanRecordCheckOutInputData inputData = captor.getValue();
    assertEquals(1, inputData.getUserId());
    assertEquals(2, inputData.getBookId());
  }

  /**
   * バリデーションエラーの場合、貸出記録登録ユースケースを呼び出さないことを検証する。
   */
  @Test
  void checkOutValidationError() throws Exception {
    mockMvc.perform(post("/loanrecords")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"userId\":\"0\",\"bookId\":\"0\"}"))
        .andExpect(status().isBadRequest());

    verify(loanRecordCheckOutUseCase, never()).handle(any());
  }
}
