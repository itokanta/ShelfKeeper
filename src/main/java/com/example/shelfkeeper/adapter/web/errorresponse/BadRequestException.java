package com.example.shelfkeeper.adapter.web.errorresponse;

/**
 * 不正なリクエストを表す例外。
 *
 * @author itokanta
 */
public class BadRequestException extends RuntimeException{

  public BadRequestException(String message) {
    super(message);
  }
}
