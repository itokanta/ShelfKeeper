package com.example.shelfkeeper.security;

import java.io.IOException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.servlet.util.matcher.PathPatternRequestMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Authorization ヘッダーの JWT を検証し、認証情報を SecurityContext に設定するフィルター。
 *
 * @author itokanta
 */
public class JwtAuthorizationFilter extends OncePerRequestFilter {
  /** Bearer トークンのプレフィックス。 */
  private static final String BEARER_PREFIX = "Bearer ";
  /** JWT の発行・検証を担うサービス。 */
  private final JwtService jwtService;
  /** ログインエンドポイントのマッチャー。 */
  private final PathPatternRequestMatcher loginMatcher = PathPatternRequestMatcher.withDefaults()
      .matcher(HttpMethod.POST, "/login");

  public JwtAuthorizationFilter(JwtService jwtService) {
    this.jwtService = jwtService;
  }

  /**
   * JWT を検証し、有効な場合は認証情報を SecurityContext に設定する。
   * トークンが無効な場合は 401 を返す。
   *
   * @param request     HTTP リクエスト
   * @param response    HTTP レスポンス
   * @param filterChain フィルターチェーン
   */
  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {

    if (loginMatcher.matches(request)) {
      filterChain.doFilter(request, response);
      return;
    }

    String header = request.getHeader(HttpHeaders.AUTHORIZATION);
    if (header == null || !header.startsWith(BEARER_PREFIX)) {
      filterChain.doFilter(request, response);
      return;
    }

    try {
      DecodedJWT jwt = jwtService.verifyToken(header.substring(BEARER_PREFIX.length()));
      LoginUser loginUser = jwtService.toLoginUser(jwt);
      UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(loginUser, null,
          loginUser.getAuthorities());

      SecurityContextHolder.getContext().setAuthentication(authentication);
      filterChain.doFilter(request, response);
    } catch (JWTVerificationException e) {
      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      response.setContentType(MediaType.APPLICATION_JSON_VALUE);
      response.setCharacterEncoding("UTF-8");
      response.getWriter().write("{\"message\":\"トークンが無効です\"}");
    }
  }
}
