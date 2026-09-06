package com.example.shelfkeeper.adapter.web.book;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.shelfkeeper.adapter.web.book.create.BookCreateRequest;
import com.example.shelfkeeper.usecase.book.create.BookCreateUseCase;
import com.example.shelfkeeper.usecase.book.delete.BookDeleteInputData;
import com.example.shelfkeeper.usecase.book.delete.BookDeleteUseCase;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;

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
  /** 蔵書削除ユースケース。 */
  private final BookDeleteUseCase bookDeleteUseCase;

  public BookController(BookCreateUseCase bookCreateUseCase, BookDeleteUseCase bookDeleteUseCase) {
    this.bookCreateUseCase = bookCreateUseCase;
    this.bookDeleteUseCase = bookDeleteUseCase;
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

  /**
   * 蔵書を削除する。
   *
   * @param id 削除対象の書籍の識別子
   * @return 削除成功時は 204 No Content
   */
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable @Min(value = 1, message = "書籍IDは1以上で指定してください") Integer id) {
    bookDeleteUseCase.handle(new BookDeleteInputData(id));
    return ResponseEntity.noContent().build();
  }
}
