package com.example.shelfkeeper.adapter.web.adminuser;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.shelfkeeper.adapter.web.adminuser.create.AdminUserCreateRequest;
import com.example.shelfkeeper.usecase.adminuser.create.AdminUserCreateUseCase;

import jakarta.validation.Valid;

/**
 * 管理者ユーザーに関する API を提供するコントローラー。
 *
 * @author itokanta
 */
@RestController
@RequestMapping("adminusers")
@CrossOrigin(origins = "http://localhost:8080", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class AdminUserController {
  /** 管理者ユーザー作成ユースケース。 */
  private final AdminUserCreateUseCase adminUserCreateUseCase;

  public AdminUserController(AdminUserCreateUseCase adminUserCreateUseCase) {
    this.adminUserCreateUseCase = adminUserCreateUseCase;
  }

  /**
   * 管理者ユーザーを作成する。
   *
   * @param request 作成リクエスト
   * @return 作成成功時は 201 Created
   */
  @PostMapping("")
  public ResponseEntity<Void> create(@RequestBody @Valid AdminUserCreateRequest request) {
    adminUserCreateUseCase.handle(request.toInputData());
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }
}
