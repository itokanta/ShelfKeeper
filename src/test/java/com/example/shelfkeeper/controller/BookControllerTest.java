package com.example.shelfkeeper.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.example.shelfkeeper.adapter.web.book.BookController;
import com.example.shelfkeeper.usecase.book.create.BookCreateInputData;
import com.example.shelfkeeper.usecase.book.create.BookCreateUseCase;

/**
 * {@link BookController} のテストクラス。
 *
 * @author itokanta
 */
@WebMvcTest(BookController.class)
@AutoConfigureMockMvc(addFilters = false)
public class BookControllerTest {
  private final MockMvc mockMvc;

  @MockitoBean
  private BookCreateUseCase bookCreateUseCase;

  @Autowired
  public BookControllerTest(MockMvc mockMvc) {
    this.mockMvc = mockMvc;
  }

  /**
   * 蔵書を作成できることを検証する。
   */
  @Test
  void createSuccess() throws Exception {
    mockMvc.perform(post("/books")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"title\":\"test\",\"authorName\":\"testName\"}"))
            .andExpect(status().isCreated());

    ArgumentCaptor<BookCreateInputData> captor = ArgumentCaptor.forClass(BookCreateInputData.class);
    verify(bookCreateUseCase).handle(captor.capture());

    BookCreateInputData inputData = captor.getValue();
    assertEquals("test", inputData.getTitle());
    assertEquals("testName", inputData.getAuthorName());
  }

  /**
   * バリデーションエラーの場合、ユースケースを呼び出さないことを検証する。
   */
  @Test
  void createValidationError() throws Exception {
    mockMvc.perform(post("/books")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"title\":\"\",\"authorName\":\"\"}"))
            .andExpect(status().isBadRequest());

    verify(bookCreateUseCase, never()).handle(any());
  }
}
