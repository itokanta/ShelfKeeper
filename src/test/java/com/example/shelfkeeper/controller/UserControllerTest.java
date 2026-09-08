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

import com.example.shelfkeeper.adapter.web.user.UserController;
import com.example.shelfkeeper.adapter.web.user.list.UserListItem;
import com.example.shelfkeeper.adapter.web.user.list.UserListPresenter;
import com.example.shelfkeeper.adapter.web.user.list.UserListResponse;
import com.example.shelfkeeper.adapter.web.user.overduelist.DueBookListItem;
import com.example.shelfkeeper.adapter.web.user.overduelist.UserOverDueListItem;
import com.example.shelfkeeper.adapter.web.user.overduelist.UserOverDueListPresenter;
import com.example.shelfkeeper.adapter.web.user.overduelist.UserOverDueListResponse;
import com.example.shelfkeeper.usecase.user.create.UserCreateInputData;
import com.example.shelfkeeper.usecase.user.create.UserCreateUseCase;
import com.example.shelfkeeper.usecase.user.delete.UserDeleteInputData;
import com.example.shelfkeeper.usecase.user.delete.UserDeleteUseCase;
import com.example.shelfkeeper.usecase.user.list.UserListUseCase;
import com.example.shelfkeeper.usecase.user.overduelist.UserOverDueListUseCase;

/**
 * {@link UserController} のテストクラス。
 *
 * @author itokanta
 */
@WebMvcTest(UserController.class)
@AutoConfigureMockMvc(addFilters = false)
public class UserControllerTest {
  private final MockMvc mockMvc;

  @MockitoBean
  private UserCreateUseCase userCreateUseCase;

  @MockitoBean
  private UserDeleteUseCase userDeleteUseCase;

  @MockitoBean
  private UserListUseCase userListUseCase;

  @MockitoBean
  private UserListPresenter userListPresenter;

  @MockitoBean
  private UserOverDueListUseCase userOverDueListUseCase;

  @MockitoBean
  private UserOverDueListPresenter userOverDueListPresenter;

  @Autowired
  public UserControllerTest(MockMvc mockMvc) {
    this.mockMvc = mockMvc;
  }

  /**
   * 利用者の作成に成功することを検証する。
   */
  @Test
  void createSuccess() throws Exception {
    mockMvc.perform(post("/users")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"name\":\"test\"}"))
        .andExpect(status().isCreated());

    ArgumentCaptor<UserCreateInputData> captor = ArgumentCaptor.forClass(UserCreateInputData.class);
    verify(userCreateUseCase).handle(captor.capture());

    assertEquals("test", captor.getValue().getName());
  }

  /**
   * 名前のバリデーションエラーの場合、作成ユースケースを呼び出さないことを検証する。
   */
  @Test
  void createValidationError() throws Exception {
    mockMvc.perform(post("/users")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"name\":\"\"}"))
        .andExpect(status().isBadRequest());

    verify(userCreateUseCase, never()).handle(any());
  }

  /**
   * 利用者の削除に成功することを検証する。
   */
  @Test
  void deleteSuccess() throws Exception {
    mockMvc.perform(delete("/users/1"))
        .andExpect(status().isNoContent());

    ArgumentCaptor<UserDeleteInputData> captor = ArgumentCaptor.forClass(UserDeleteInputData.class);
    verify(userDeleteUseCase).handle(captor.capture());

    assertEquals(1, captor.getValue().getId());
  }

  /**
   * ユーザーIDのバリデーションエラーの場合、削除ユースケースを呼び出さないことを検証する。
   */
  @Test
  void deleteValidationError() throws Exception {
    mockMvc.perform(delete("/users/0"))
        .andExpect(status().isBadRequest());

    verify(userDeleteUseCase, never()).handle(any());
  }

  /**
   * 利用者一覧の取得に成功することを検証する。
   */
  @Test
  void listSuccess() throws Exception {
    UserListResponse response = new UserListResponse(List.of(new UserListItem(1, "test")));
    when(userListPresenter.getResponse()).thenReturn(response);

    mockMvc.perform(get("/users"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.userList[0].id").value(1))
        .andExpect(jsonPath("$.userList[0].name").value("test"));

    verify(userListUseCase).handle();
  }

  /**
   * 延滞利用者一覧の取得に成功することを検証する。
   */
  @Test
  void overDueListSuccess() throws Exception {
    List<DueBookListItem> dueBookList = List.of(new DueBookListItem(
      1,
      "testTitle",
      LocalDate.of(2026, 8, 25),
      LocalDate.of(2026, 8, 29)
    ));
    UserOverDueListResponse response = new UserOverDueListResponse(List.of(new UserOverDueListItem(2, "testName", dueBookList)));

    when(userOverDueListPresenter.getResponse()).thenReturn(response);

    mockMvc.perform(get("/users/overduelist"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.userOverDueList[0].userId").value(2))
        .andExpect(jsonPath("$.userOverDueList[0].userName").value("testName"))
        .andExpect(jsonPath("$.userOverDueList[0].dueBookList[0].bookId").value(1))
        .andExpect(jsonPath("$.userOverDueList[0].dueBookList[0].bookTitle").value("testTitle"))
        .andExpect(jsonPath("$.userOverDueList[0].dueBookList[0].loanDate").value("2026-08-25"))
        .andExpect(jsonPath("$.userOverDueList[0].dueBookList[0].dueDate").value("2026-08-29"));

    verify(userOverDueListUseCase).handle();
  }
}
