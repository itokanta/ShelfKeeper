package com.example.shelfkeeper.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.example.shelfkeeper.adapter.web.book.BookController;
import com.example.shelfkeeper.adapter.web.book.list.BookListItem;
import com.example.shelfkeeper.adapter.web.book.list.BookListPresenter;
import com.example.shelfkeeper.adapter.web.book.list.BookListResponse;
import com.example.shelfkeeper.adapter.web.book.search.BookSearchItem;
import com.example.shelfkeeper.adapter.web.book.search.BookSearchPresenter;
import com.example.shelfkeeper.adapter.web.book.search.BookSearchResponse;
import com.example.shelfkeeper.usecase.book.create.BookCreateInputData;
import com.example.shelfkeeper.usecase.book.create.BookCreateUseCase;
import com.example.shelfkeeper.usecase.book.delete.BookDeleteInputData;
import com.example.shelfkeeper.usecase.book.delete.BookDeleteUseCase;
import com.example.shelfkeeper.usecase.book.list.BookListUseCase;
import com.example.shelfkeeper.usecase.book.search.BookSearchInputData;
import com.example.shelfkeeper.usecase.book.search.BookSearchUseCase;

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

  @MockitoBean
  private BookDeleteUseCase bookDeleteUseCase;

  @MockitoBean
  private BookListUseCase bookListUseCase;

  @MockitoBean
  private BookListPresenter bookListPresenter;

  @MockitoBean
  private BookSearchUseCase bookSearchUseCase;

  @MockitoBean
  private BookSearchPresenter bookSearchPresenter;

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

  /**
   * 蔵書を削除できることを検証する。
   */
  @Test
  void deleteSuccess() throws Exception {
    mockMvc.perform(delete("/books/1"))
        .andExpect(status().isNoContent());

    ArgumentCaptor<BookDeleteInputData> captor = ArgumentCaptor.forClass(BookDeleteInputData.class);
    verify(bookDeleteUseCase).handle(captor.capture());

    assertEquals(1, captor.getValue().getId());
  }

  /**
   * 書籍IDのバリデーションエラーの場合、削除ユースケースを呼び出さないことを検証する。
   */
  @Test
  void deleteValidationError() throws Exception {
    mockMvc.perform(delete("/books/0"))
        .andExpect(status().isBadRequest());

    verify(bookDeleteUseCase, never()).handle(any());
  }

  /**
   * 蔵書一覧の取得に成功することを検証する。
   */
  @Test
  void listSuccess() throws Exception {
    BookListResponse response = new BookListResponse(List.of(new BookListItem(1, "test", "testName", "貸出可能")));
    when(bookListPresenter.getResponse()).thenReturn(response);

    mockMvc.perform(get("/books"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.bookList[0].bookId").value(1))
        .andExpect(jsonPath("$.bookList[0].title").value("test"))
        .andExpect(jsonPath("$.bookList[0].authorName").value("testName"))
        .andExpect(jsonPath("$.bookList[0].status").value("貸出可能"));

    verify(bookListUseCase).handle();
  }

  /**
   * 蔵書検索に成功することを検証する。
   */
  @Test
  void searchSuccess() throws Exception {
    BookSearchResponse response = new BookSearchResponse(List.of(new BookSearchItem(1, "test", "testName", "貸出可能")));
    when(bookSearchPresenter.getResponse()).thenReturn(response);

    mockMvc.perform(get("/books/search")
        .param("title", "test"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.searchBookList[0].bookId").value(1))
        .andExpect(jsonPath("$.searchBookList[0].title").value("test"))
        .andExpect(jsonPath("$.searchBookList[0].authorName").value("testName"))
        .andExpect(jsonPath("$.searchBookList[0].status").value("貸出可能"));

    ArgumentCaptor<BookSearchInputData> captor = ArgumentCaptor.forClass(BookSearchInputData.class);
    verify(bookSearchUseCase).handle(captor.capture());

    assertEquals("test", captor.getValue().getTitle());
  }

  /**
   * タイトルのバリデーションエラーの場合、検索ユースケースを呼び出さないことを検証する。
   */
  @Test
  void searchValidationError() throws Exception {
    mockMvc.perform(get("/books/search"))
        .andExpect(status().isBadRequest());

    verify(bookSearchUseCase, never()).handle(any());
  }
}
