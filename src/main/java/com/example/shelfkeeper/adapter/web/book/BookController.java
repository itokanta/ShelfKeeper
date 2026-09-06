package com.example.shelfkeeper.adapter.web.book;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.shelfkeeper.adapter.web.book.create.BookCreateRequest;
import com.example.shelfkeeper.usecase.book.create.BookCreateUseCase;

import jakarta.validation.Valid;

/**
 * 蔵書に関する API を提供するコントローラー。
 *
 * @author itokanta
 */
@RestController
@RequestMapping("books")
@CrossOrigin(origins = "http://localhost:8080", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class BookController {

  /** 蔵書作成ユースケース。 */
  private final BookCreateUseCase bookCreateUseCase;

  public BookController(BookCreateUseCase bookCreateUseCase) {
    this.bookCreateUseCase = bookCreateUseCase;
  }

  /**
   * 蔵書を作成する。
   *
   * @param request 作成リクエスト
   * @return 作成成功時は 201 Created
   */
  @PostMapping("")
  public ResponseEntity<Void> create(@RequestBody @Valid BookCreateRequest request) {
    bookCreateUseCase.handle(request.toBookCreateInputData());
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }
}
