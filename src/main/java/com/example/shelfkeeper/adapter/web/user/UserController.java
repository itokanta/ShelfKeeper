package com.example.shelfkeeper.adapter.web.user;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.shelfkeeper.adapter.web.user.create.UserCreateRequest;
import com.example.shelfkeeper.adapter.web.user.list.UserListPresenter;
import com.example.shelfkeeper.adapter.web.user.list.UserListResponse;
import com.example.shelfkeeper.adapter.web.user.overduelist.UserOverDueListPresenter;
import com.example.shelfkeeper.adapter.web.user.overduelist.UserOverDueListResponse;
import com.example.shelfkeeper.usecase.user.create.UserCreateUseCase;
import com.example.shelfkeeper.usecase.user.delete.UserDeleteInputData;
import com.example.shelfkeeper.usecase.user.delete.UserDeleteUseCase;
import com.example.shelfkeeper.usecase.user.list.UserListUseCase;
import com.example.shelfkeeper.usecase.user.overduelist.UserOverDueListUseCase;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;

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
  /** 利用者削除ユースケース。 */
  private final UserDeleteUseCase userDeleteUseCase;
  /** 利用者一覧取得ユースケース。 */
  private final UserListUseCase userListUseCase;
  /** 利用者一覧取得のプレゼンター。 */
  private final UserListPresenter userListPresenter;
  /** 延滞利用者一覧取得ユースケース。 */
  private final UserOverDueListUseCase userOverDueListUseCase;
  /** 延滞利用者一覧取得のプレゼンター。 */
  private final UserOverDueListPresenter userOverDueListPresenter;

  public UserController(UserCreateUseCase userCreateUseCase, UserDeleteUseCase userDeleteUseCase,
      UserListUseCase userListUseCase, UserListPresenter userListPresenter,
      UserOverDueListUseCase userOverDueListUseCase, UserOverDueListPresenter userOverDueListPresenter) {
    this.userCreateUseCase = userCreateUseCase;
    this.userDeleteUseCase = userDeleteUseCase;
    this.userListUseCase = userListUseCase;
    this.userListPresenter = userListPresenter;
    this.userOverDueListUseCase = userOverDueListUseCase;
    this.userOverDueListPresenter = userOverDueListPresenter;
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

  /**
   * 利用者を削除する。
   *
   * @param id 削除対象の利用者の識別子
   * @return 削除成功時は 204 No Content
   */
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable @Min(value = 1, message = "ユーザーIDは1以上を指定してください") Integer id) {
    userDeleteUseCase.handle(new UserDeleteInputData(id));
    return ResponseEntity.noContent().build();
  }

  /**
   * 利用者一覧を取得する。
   *
   * @return 利用者一覧
   */
  @GetMapping("")
  public ResponseEntity<UserListResponse> list() {
    userListUseCase.handle();
    return ResponseEntity.ok(userListPresenter.getResponse());
  }

  /**
   * 延滞利用者一覧を取得する。
   *
   * @return 延滞利用者一覧
   */
  @GetMapping("/overduelist")
  public ResponseEntity<UserOverDueListResponse> overDueList() {
    userOverDueListUseCase.handle();
    return ResponseEntity.ok(userOverDueListPresenter.getResponse());
  }
}
