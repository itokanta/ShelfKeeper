package com.example.shelfkeeper.security;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.context.SecurityContextHolder;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.shelfkeeper.domain.adminuser.AdminUser;

import jakarta.servlet.http.HttpServletResponse;

/**
 * {@link JwtAuthorizationFilter} のテストクラス。
 *
 * @author itokanta
 */
@ExtendWith(MockitoExtension.class)
public class JwtAuthorizationFilterTest {

  @Mock
  private JwtService jwtService;

  @Mock
  private DecodedJWT decodedJWT;

  private JwtAuthorizationFilter filter;

  @BeforeEach
  void setUp() {
    filter = new JwtAuthorizationFilter(jwtService);
  }

  @AfterEach
  void tearDown() {
    SecurityContextHolder.clearContext();
  }

  /**
   * ログインリクエストの場合、JWT 検証を行わないことを検証する。
   */
  @Test
  void skipsLogin() throws Exception {
    MockHttpServletRequest request = request("POST", "/login");
    MockHttpServletResponse response = new MockHttpServletResponse();

    filter.doFilter(request, response, new MockFilterChain());

    verify(jwtService, never()).verifyToken(any());
    assertNull(SecurityContextHolder.getContext().getAuthentication());
  }

  /**
   * Authorization ヘッダーがない場合、認証情報を設定しないことを検証する。
   */
  @Test
  void continuesWhenNoAuthorizationHeader() throws Exception {
    MockHttpServletRequest request = request("GET", "/me");
    MockHttpServletResponse response = new MockHttpServletResponse();

    filter.doFilter(request, response, new MockFilterChain());

    verify(jwtService, never()).verifyToken(any());
    assertNull(SecurityContextHolder.getContext().getAuthentication());
  }

  /**
   * 有効な JWT の場合、LoginUser を SecurityContext に設定することを検証する。
   */
  @Test
  void setsLoginUserWhenTokenIsValid() throws Exception {
    LoginUser loginUser = new LoginUser(new AdminUser(1, "test", "test@test", ""));

    when(jwtService.verifyToken("valid.token")).thenReturn(decodedJWT);
    when(jwtService.toLoginUser(decodedJWT)).thenReturn(loginUser);

    MockHttpServletRequest request = request("GET", "/me");
    request.addHeader(HttpHeaders.AUTHORIZATION, "Bearer valid.token");
    MockHttpServletResponse response = new MockHttpServletResponse();

    filter.doFilter(request, response, new MockFilterChain());

    LoginUser principal = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    assertEquals(1, principal.getAdminUser().getId());
    assertEquals("test", principal.getAdminUser().getName());
    assertEquals("test@test", principal.getUsername());
    assertEquals(HttpServletResponse.SC_OK, response.getStatus());
  }

  /**
   * Bearer 形式でない Authorization ヘッダーの場合、JWT 検証を行わないことを検証する。
   */
  @Test
  void continuesWhenAuthorizationHeaderIsNotBearer() throws Exception {
    MockHttpServletRequest request = request("GET", "/me");
    request.addHeader(HttpHeaders.AUTHORIZATION, "Basic abc");
    MockHttpServletResponse response = new MockHttpServletResponse();

    filter.doFilter(request, response, new MockFilterChain());

    verify(jwtService, never()).verifyToken(any());
    assertNull(SecurityContextHolder.getContext().getAuthentication());
  }

  /**
   * 無効な JWT の場合、401 を返すことを検証する。
   */
  @Test
  void returns401WhenTokenIsInvalid() throws Exception {
    when(jwtService.verifyToken("bad.token")).thenThrow(new JWTVerificationException("invalid"));

    MockHttpServletRequest request = request("GET", "/me");
    request.addHeader(HttpHeaders.AUTHORIZATION, "Bearer bad.token");
    MockHttpServletResponse response = new MockHttpServletResponse();

    filter.doFilter(request, response, new MockFilterChain());

    assertEquals(HttpServletResponse.SC_UNAUTHORIZED, response.getStatus());
    assertTrue(response.getContentAsString().contains("トークンが無効です"));
    assertNull(SecurityContextHolder.getContext().getAuthentication());
  }

  private MockHttpServletRequest request(String method, String path) {
    MockHttpServletRequest request = new MockHttpServletRequest();
    request.setMethod(method);
    request.setRequestURI(path);
    request.setServletPath(path);
    return request;
  }
}
