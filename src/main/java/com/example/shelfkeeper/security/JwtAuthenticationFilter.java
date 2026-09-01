package com.example.shelfkeeper.security;

import java.io.IOException;

import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.servlet.util.matcher.PathPatternRequestMatcher;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * {@code POST /login} でメールアドレスとパスワードを受け取り、JWT を発行するフィルター。
 *
 * @author itokanta
 */
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

  /** JWT の発行・検証を担うサービス。 */
  private final JwtService jwtService;
  /** リクエストボディの JSON 変換に用いるマッパー。 */
  private final ObjectMapper objectMapper;

  public JwtAuthenticationFilter(
      AuthenticationManager authenticationManager,
      JwtService jwtService,
      ObjectMapper objectMapper) {
    this.jwtService = jwtService;
    this.objectMapper = objectMapper;
    setAuthenticationManager(authenticationManager);
    setRequiresAuthenticationRequestMatcher(PathPatternRequestMatcher.withDefaults().matcher(HttpMethod.POST,"/login"));
    setAuthenticationFailureHandler((request, response, exception) -> {
      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      response.setContentType(MediaType.APPLICATION_JSON_VALUE);
      response.setCharacterEncoding("UTF-8");
      response.getWriter().write("{\"message\":\"メールアドレスまたはパスワードが正しくありません\"}");
    });
  }

  /**
   * リクエストボディから認証情報を読み取り、認証を試みる。
   *
   * @param request HTTP リクエスト
   * @param response HTTP レスポンス
   * @return 認証結果
   * @throws AuthenticationException 認証に失敗した場合
   */
  @Override
  public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
      throws AuthenticationException {
    try {
      LoginRequest loginRequest = objectMapper.readValue(request.getInputStream(), LoginRequest.class);
      UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(loginRequest.getMail(), loginRequest.getPass());
      return getAuthenticationManager().authenticate(authRequest);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * 認証成功時に JWT をレスポンスへ返す。
   *
   * @param request HTTP リクエスト
   * @param response HTTP レスポンス
   * @param chain フィルターチェーン
   * @param authResult 認証結果
   */
  @Override
  protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
      Authentication authResult) throws IOException, ServletException {
    LoginUser loginUser = (LoginUser) authResult.getPrincipal();
    String token = jwtService.createToken(loginUser);

    response.setStatus(HttpServletResponse.SC_OK);
    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
    response.setCharacterEncoding("UTF-8");
    response.getWriter().write("{\"token\":\"" + token + "\"}");
  }
}
