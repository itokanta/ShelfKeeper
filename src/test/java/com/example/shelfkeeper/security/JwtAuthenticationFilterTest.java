package com.example.shelfkeeper.security;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import com.example.shelfkeeper.domain.adminuser.AdminUser;

import tools.jackson.databind.json.JsonMapper;

import jakarta.servlet.http.HttpServletResponse;

/**
 * {@link JwtAuthenticationFilter} のテストクラス。
 *
 * @author itokanta
 */
@ExtendWith(MockitoExtension.class)
public class JwtAuthenticationFilterTest {

  @Mock
  private AuthenticationManager authenticationManager;
  
  @Mock
  private JwtService jwtService;

  private JwtAuthenticationFilter filter;

  @BeforeEach
  void setUp() {
    filter = new JwtAuthenticationFilter(authenticationManager, jwtService, new JsonMapper());
  }

  /**
   * ログイン成功時に JWT を返すことを検証する。
   */
  @Test
  void returnsTokenWhenLoginSucceeds() throws Exception {
    LoginUser loginUser = new LoginUser(new AdminUser(1, "test", "test@test", "testPassHashed"));
    Authentication success = new UsernamePasswordAuthenticationToken(loginUser, null, loginUser.getAuthorities());
    
    when(authenticationManager.authenticate(any())).thenReturn(success);
    when(jwtService.createToken(loginUser)).thenReturn("issued.jwt.token");

    MockHttpServletRequest request = loginRequest("{\"mail\":\"test@test\",\"pass\":\"testPass\"}");
    MockHttpServletResponse response = new MockHttpServletResponse();

    filter.doFilter(request, response, new MockFilterChain());

    ArgumentCaptor<Authentication> captor = ArgumentCaptor.forClass(Authentication.class);
    verify(authenticationManager).authenticate(captor.capture());

    Authentication result = captor.getValue();
    assertEquals("test@test", result.getPrincipal());
    assertEquals("testPass", result.getCredentials());
    assertEquals(HttpServletResponse.SC_OK, response.getStatus());
    assertTrue(response.getContentAsString().contains("issued.jwt.token"));
  }

  /**
   * ログイン失敗時に 401 を返すことを検証する。
   */
  @Test
  void returns401WhenLoginFails() throws Exception {
    when(authenticationManager.authenticate(any())).thenThrow(new BadCredentialsException("bad credentials"));

    MockHttpServletRequest request = loginRequest("{\"mail\":\"test@test.com.com\",\"pass\":\"testPassTestPass\"}");
    MockHttpServletResponse response = new MockHttpServletResponse();

    filter.doFilter(request, response, new MockFilterChain());

    assertEquals(HttpServletResponse.SC_UNAUTHORIZED, response.getStatus());
    assertTrue(response.getContentAsString().contains("メールアドレスまたはパスワードが正しくありません"));
    verify(jwtService, never()).createToken(any());
  }

  /**
   * リクエストボディが JSON でない場合、例外をスローすることを検証する。
   */
  @Test
  void throwsWhenRequestBodyIsNotJson() {
    MockHttpServletRequest request = loginRequest("not-json");
    MockHttpServletResponse response = new MockHttpServletResponse();

    assertThrows(RuntimeException.class, () -> filter.doFilter(request, response, new MockFilterChain()));

    verify(authenticationManager, never()).authenticate(any());
    verify(jwtService, never()).createToken(any());
  }

  /**
   * ログイン以外のパスでは認証を試みないことを検証する。
   */
  @Test
  void doesNotAuthenticateWhenPathIsNotLogin() throws Exception {
    MockHttpServletRequest request = new MockHttpServletRequest();
    request.setMethod("GET");
    request.setRequestURI("/me");
    request.setServletPath("/me");

    filter.doFilter(request, new MockHttpServletResponse(), new MockFilterChain());

    verify(authenticationManager, never()).authenticate(any());
  }

  private MockHttpServletRequest loginRequest(String json) {
    MockHttpServletRequest request = new MockHttpServletRequest();
    request.setMethod("POST");
    request.setRequestURI("/login");
    request.setServletPath("login");
    request.setContentType("application/json");
    request.setContent(json.getBytes(StandardCharsets.UTF_8));
    return request;
  }
}
