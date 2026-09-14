package com.example.shelfkeeper.adapter.web.errorresponse;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * API エラーレスポンス。
 *
 * @author itokanta
 */
public class ErrorResponse {
  /** HTTP ステータスコード。 */
  private Integer status;
  /** エラーメッセージ。 */
  private String message;

  /** 項目ごとのエラー詳細。存在しない場合はレスポンスに含めない。 */
  @JsonInclude(JsonInclude.Include.NON_NULL)
  private Map<String, String> details;

  public ErrorResponse(Integer status, String message, Map<String, String> details) {
    this.status = status;
    this.message = message;
    this.details = details;
  }

  public Integer getStatus() {
    return status;
  }

  public String getMessage() {
    return message;
  }

  public Map<String, String> getDetails() {
    return details;
  }

  public void setStatus(Integer status) {
    this.status = status;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public void setDetails(Map<String, String> details) {
    this.details = details;
  }
}
