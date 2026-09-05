package com.example.shelfkeeper.errorresponse;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.example.shelfkeeper.adapter.web.adminuser.AdminUserController;
import com.example.shelfkeeper.adapter.web.errorresponse.BadRequestException;
import com.example.shelfkeeper.adapter.web.errorresponse.GlobalExceptionHandler;
import com.example.shelfkeeper.usecase.adminuser.create.AdminUserCreateUseCase;

/**
 * {@link GlobalExceptionHandler} のテストクラス。
 *
 * @author itokanta
 */
@WebMvcTest(controllers = AdminUserController.class)
@AutoConfigureMockMvc(addFilters = false)
public class GlobalExceptionHandlerTest {

  private final MockMvc mockMvc;

  @MockitoBean
  private AdminUserCreateUseCase adminUserCreateUseCase;

  @Autowired
  public GlobalExceptionHandlerTest(MockMvc mockMvc) {
    this.mockMvc = mockMvc;
  }

  /**
   * バリデーションエラー時に 400 と詳細メッセージを返すことを検証する。
   */
  @Test
  void handleValidationError() throws Exception {
    mockMvc.perform(post("/adminusers")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"name\":\"\",\"mail\":\"test\",\"pass\":\"testTest\"}"))
          .andExpect(status().isBadRequest())
          .andExpect(jsonPath("$.status").value(400))
          .andExpect(jsonPath("$.message").value("バリデーションエラー"))
          .andExpect(jsonPath("$.details.name").value("名前は必須です"))
          .andExpect(jsonPath("$.details.mail").value("メールアドレスとして不正な形式です"));
    verify(adminUserCreateUseCase, never()).handle(any());
  }

  /**
   * {@link BadRequestException} 発生時に 400 を返すことを検証する。
   */
  @Test
  void handleBadRequestException() throws Exception {
    doThrow(new BadRequestException("入力されたメールアドレスはすでに登録されています")).when(adminUserCreateUseCase).handle(any());

    mockMvc.perform(post("/adminusers")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"name\":\"test\",\"mail\":\"test@test.com\",\"pass\":\"testTest\"}"))
          .andExpect(status().isBadRequest())
          .andExpect(jsonPath("$.status").value(400))
          .andExpect(jsonPath("$.message").value("入力されたメールアドレスはすでに登録されています"))
          .andExpect(jsonPath("$.details").doesNotExist());
  }

  /**
   * 予期しない例外発生時に 500 を返すことを検証する。
   */
  @Test
  void handleUnexpectedException() throws Exception {
    doThrow(new RuntimeException("予期しないエラー")).when(adminUserCreateUseCase).handle(any());

    mockMvc.perform(post("/adminusers")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"name\":\"test\",\"mail\":\"test@test.com\",\"pass\":\"testTest\"}"))
          .andExpect(status().isInternalServerError())
          .andExpect(jsonPath("$.status").value(500))
          .andExpect(jsonPath("$.message").value("サーバーエラーが発生しました"))
          .andExpect(jsonPath("$.details").doesNotExist());
  }

  /**
   * 正常リクエスト時に例外ハンドラーが介入しないことを検証する。
   */
  @Test
  void noErrorValid() throws Exception {
    mockMvc.perform(post("/adminusers")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"name\":\"test\",\"mail\":\"test@test.com\",\"pass\":\"testTest\"}"))
          .andExpect(status().isCreated());
    verify(adminUserCreateUseCase).handle(any());
  }
}
