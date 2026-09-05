package com.example.shelfkeeper.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.example.shelfkeeper.usecase.adminuser.create.AdminUserCreateInputData;
import com.example.shelfkeeper.usecase.adminuser.create.AdminUserCreateUseCase;

/**
 * {@link com.example.shelfkeeper.adapter.web.adminuser.AdminUserController} のテストクラス。
 *
 * @author itokanta
 */
@WebMvcTest
@AutoConfigureMockMvc(addFilters =  false)
public class AdminUserControllerTest {

  private final MockMvc mockMvc;

  @MockitoBean
  private AdminUserCreateUseCase adminUserCreateUseCase;

  public AdminUserControllerTest(MockMvc mockMvc) {
    this.mockMvc = mockMvc;
  }

  /**
   * 管理者ユーザーを作成できることを検証する。
   */
  @Test
  void createSuccess() throws Exception {
    mockMvc.perform(post("/adminusers")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"name\":\"test\",\"mail\":\"test@test.com\",\"pass\":\"testTest\"}"))
            .andExpect(status().isCreated());

    ArgumentCaptor<AdminUserCreateInputData> captor = ArgumentCaptor.forClass(AdminUserCreateInputData.class);
    verify(adminUserCreateUseCase).handle(captor.capture());;

    AdminUserCreateInputData inputData = captor.getValue();
    assertEquals("test", inputData.getName());
    assertEquals("test@test.com", inputData.getMail());
    assertEquals("testTest", inputData.getPass());
  }

  /**
   * バリデーションエラーの場合、ユースケースを呼び出さないことを検証する。
   */
  @Test
  void createValidationError() throws Exception {
    mockMvc.perform(post("/adminusers")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"name\":\"test\",\"mail\":\"\":\"testTest\"}"))
            .andExpect(status().isCreated());

    verify(adminUserCreateUseCase, never()).handle(any());
  }
}
