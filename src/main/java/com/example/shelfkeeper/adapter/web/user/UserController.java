package com.example.shelfkeeper.adapter.web.user;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.shelfkeeper.adapter.web.user.create.UserCreateRequest;
import com.example.shelfkeeper.usecase.user.create.UserCreateUseCase;

import jakarta.validation.Valid;

/**
 * 利用者に関する API を提供するコントローラー。
 *
 * @author itokanta
 */
@RestController
@RequestMapping("users")
@CrossOrigin(origins = "http://localhost:8080", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class UserController {
  /** 利用者作成ユースケース。 */
  private final UserCreateUseCase userCreateUseCase;

  public UserController(UserCreateUseCase userCreateUseCase) {
    this.userCreateUseCase = userCreateUseCase;
  }

  /**
   * 利用者を作成する。
   *
   * @param request 作成リクエスト
   * @return 作成成功時は 201 Created
   */
  @PostMapping("")
  public ResponseEntity<Void> create(@RequestBody @Valid UserCreateRequest request) {
    userCreateUseCase.handle(request.toUserCreateInputData());
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }
}
