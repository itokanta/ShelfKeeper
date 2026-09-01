package com.example.shelfkeeper.security;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.shelfkeeper.domain.adminuser.AdminUser;

/**
 * {@link JwtService} のテストクラス。
 *
 * @author itokanta
 */
public class JwtServiceTest {
  private static final String SECRET = "change-me-to-a-long-random-secret-key";
  private JwtService jwtService;
  private LoginUser loginUser;

  @BeforeEach
  void setUp() {
    jwtService = new JwtService(SECRET, 3600000L);
    loginUser = new LoginUser(new AdminUser(1, "test", "test@test", "testPass"));
  }

  /**
   * JWT を発行し、検証できることを検証する。
   */
  @Test
  void createAndVerifyToken() {
    String token = jwtService.createToken(loginUser);
    DecodedJWT jwt = jwtService.verifyToken(token);

    assertEquals("test@test", jwt.getSubject());
    assertEquals("shelfkeeper", jwt.getIssuer());
    assertEquals(1, jwt.getClaim("adminId").asInt());
    assertEquals("test", jwt.getClaim("name").asString());
  }

  /**
   * JWT から LoginUser を復元できることを検証する。
   */
  @Test
  void toLoginUser() {
    String token = jwtService.createToken(loginUser);
    LoginUser restored = jwtService.toLoginUser(jwtService.verifyToken(token));

    assertEquals(1, restored.getAdminUser().getId());
    assertEquals("test", restored.getAdminUser().getName());
    assertEquals("test@test", restored.getAdminUser().getMail());
    assertEquals("", restored.getAdminUser().getPass());
  }

  /**
   * 改ざんされた JWT の検証が失敗することを検証する。
   */
  @Test
  void verifyTokenFailsWhenTampered() {
    String token = jwtService.createToken(loginUser);

    assertThrows(JWTVerificationException.class, () -> jwtService.verifyToken(token + "a"));
  }

  /**
   * 期限切れ JWT の検証が失敗することを検証する。
   */
  @Test
  void verifyTokenFailsWhenExpired() {
    JwtService shortLived = new JwtService(SECRET, -1000);
    String token = shortLived.createToken(loginUser);

    assertThrows(JWTVerificationException.class, () -> shortLived.verifyToken(token));
  }

  /**
   * 署名鍵が異なる JWT の検証が失敗することを検証する。
   */
  @Test
  void verifyTokenFailsWhenSecretDiffers() {
    String token = jwtService.createToken(loginUser);
    JwtService other = new JwtService("another-secret-key-32bytes-min!!", 3600000);

    assertThrows(JWTVerificationException.class, () -> other.verifyToken(token));
  }

  /**
   * 発行した JWT が3部構成であることを検証する。
   */
  @Test
  void createTokenContainsThreeParts() {
    String token = jwtService.createToken(loginUser);

    assertEquals(3, token.split("\\.").length);
    assertTrue(token.length() > 20);
  }
}
