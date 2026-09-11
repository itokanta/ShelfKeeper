package com.example.shelfkeeper.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import tools.jackson.databind.json.JsonMapper;

/**
 * Spring Security の設定。
 * JWT ベースのステートレス認証を構成する。
 *
 * @author itokanta
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {
  
  /**
   * パスワードエンコーダーを提供する。
   *
   * @return BCrypt パスワードエンコーダー
   */
  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  /**
   * 認証マネージャーを提供する。
   *
   * @param userDetailsService ユーザー情報取得サービス
   * @param passwordEncoder パスワードエンコーダー
   * @return 認証マネージャー
   */
  @Bean
  public AuthenticationManager authenticationManager(
      UserDetailsService userDetailsService,
      PasswordEncoder passwordEncoder) {
    DaoAuthenticationProvider provider = new DaoAuthenticationProvider(userDetailsService);
    provider.setPasswordEncoder(passwordEncoder);
    return new ProviderManager(provider);
  }

  /**
   * セキュリティフィルターチェーンを構成する。
   *
   * @param http HTTP セキュリティ設定
   * @param authenticationManager 認証マネージャー
   * @param jwtService JWT サービス
   * @param jsonMapper JSON マッパー
   * @return セキュリティフィルターチェーン
   */
  @Bean
  public SecurityFilterChain securityFilterChain(
      HttpSecurity http,
      AuthenticationManager authenticationManager,
      JwtService jwtService,
      JsonMapper jsonMapper) throws Exception {
    JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(authenticationManager, jwtService, jsonMapper);
    JwtAuthorizationFilter jwtAuthorizationFilter = new JwtAuthorizationFilter(jwtService);

    http
        .csrf(csrf -> csrf.disable())
        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .formLogin(form -> form.disable())
        .httpBasic(basic -> basic.disable())
        .exceptionHandling(ex -> ex
            .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)))
        .authorizeHttpRequests(auth -> auth
            .requestMatchers("/login").permitAll()
            .anyRequest().authenticated())
        .addFilter(jwtAuthenticationFilter)
        .addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class);

    return http.build();
  }
}
