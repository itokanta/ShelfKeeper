package com.example.shelfkeeper.security;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.shelfkeeper.domain.adminuser.AdminUser;

/**
 * JWT の発行・検証を担うサービス。
 *
 * @author itokanta
 */
@Component
public class JwtService {

  /** 署名アルゴリズム。 */
  private final Algorithm algorithm;
  /** トークンの有効期限（ミリ秒）。 */
  private final long expirationMs;

  public JwtService(@Value("${jwt.secret}") String jwtSecret, @Value("${jwt.expiration-ms}") long expirationMs) {
    this.algorithm = Algorithm.HMAC256(jwtSecret);
    this.expirationMs = expirationMs;
  }

  /**
   * ログインユーザー情報から JWT を発行する。
   *
   * @param loginUser 認証済みの管理者ユーザー
   * @return 発行した JWT
   */
  public String createToken(LoginUser loginUser) {
    Date now = new Date();
    Date expire = new Date(now.getTime() + expirationMs);

    return JWT.create()
        .withIssuer("shelfkeeper")
        .withSubject(loginUser.getUsername())
        .withClaim("adminId", loginUser.getAdminUser().getId())
        .withClaim("name", loginUser.getAdminUser().getName())
        .withIssuedAt(now)
        .withExpiresAt(expire)
        .sign(algorithm);
  }

  /**
   * JWT を検証する。
   *
   * @param token 検証対象の JWT
   * @return デコードされた JWT
   */
  public DecodedJWT verifyToken(String token) {
    return JWT.require(algorithm).withIssuer("shelfkeeper").build().verify(token);
  }

  /**
   * デコードされた JWT から {@link LoginUser} を復元する。
   *
   * @param jwt デコードされた JWT
   * @return 復元したログインユーザー
   */
  public LoginUser toLoginUser(DecodedJWT jwt) {
    AdminUser adminUser = new AdminUser(
        jwt.getClaim("adminId").asInt(),
        jwt.getClaim("name").asString(),
        jwt.getSubject(),
        "");
    return new LoginUser(adminUser);
  }
}
