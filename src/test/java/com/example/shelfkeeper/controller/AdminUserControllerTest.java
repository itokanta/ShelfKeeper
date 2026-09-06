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

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.example.shelfkeeper.adapter.web.adminuser.findsingle.AdminUserFindSinglePresenter;
import com.example.shelfkeeper.adapter.web.adminuser.findsingle.AdminUserFindSingleResponse;
import com.example.shelfkeeper.domain.adminuser.AdminUser;
import com.example.shelfkeeper.security.LoginUser;
import com.example.shelfkeeper.usecase.adminuser.create.AdminUserCreateInputData;
import com.example.shelfkeeper.usecase.adminuser.create.AdminUserCreateUseCase;
import com.example.shelfkeeper.usecase.adminuser.delete.AdminUserDeleteInputData;
import com.example.shelfkeeper.usecase.adminuser.delete.AdminUserDeleteUseCase;
import com.example.shelfkeeper.usecase.adminuser.findsingle.AdminUserFindSingleInputData;
import com.example.shelfkeeper.usecase.adminuser.findsingle.AdminUserFindSingleUseCase;

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

  @MockitoBean
  private  AdminUserDeleteUseCase adminUserDeleteUseCase;

  @MockitoBean
  private AdminUserFindSingleUseCase adminUserFindSingleUseCase;

  @MockitoBean
  private AdminUserFindSinglePresenter adminUserFindSinglePresenter;

  @Autowired 
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
            .content("{\"name\":\"test\",\"mail\":\"\",\"pass\":\"testTest\"}"))
            .andExpect(status().isBadRequest());

    verify(adminUserCreateUseCase, never()).handle(any());
  }

  /**
   * ログイン中の管理者ユーザーを削除できることを検証する。
   */
  @Test 
  void deleteSuccess() throws Exception {
    LoginUser loginUser = new LoginUser(new AdminUser(1, "test", "test@test", "testHashed"));

    mockMvc.perform(delete("/adminusers")
            .with(request -> {
              SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(loginUser, null, loginUser.getAuthorities())
              );
              return request;
            }))
            .andExpect(status().isNoContent());
    ArgumentCaptor<AdminUserDeleteInputData> captor = ArgumentCaptor.forClass(AdminUserDeleteInputData.class);
    verify(adminUserDeleteUseCase).handle(captor.capture());

    assertEquals(1, captor.getValue().getId());
  }

  /**
   * ログイン中の管理者ユーザーを1件取得できることを検証する。
   */
  @Test
  void findSingleSuccess() throws Exception {
    LoginUser loginUser = new LoginUser(new AdminUser(1, "test", "test@test", "testHashed"));
    AdminUserFindSingleResponse response = new AdminUserFindSingleResponse("test", "test@test");

    when(adminUserFindSinglePresenter.getResponse()).thenReturn(response);

    mockMvc.perform(get("/adminusers/me")
            .with(request -> {
              SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(loginUser, null, loginUser.getAuthorities())
              );
              return request;
            }))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.name").value("test"))
            .andExpect(jsonPath("$.mail").value("test@test"));

    ArgumentCaptor<AdminUserFindSingleInputData> captor = ArgumentCaptor.forClass(AdminUserFindSingleInputData.class);
    verify(adminUserFindSingleUseCase).handle(captor.capture());

    assertEquals(1, captor.getValue().getId());
  }
}
