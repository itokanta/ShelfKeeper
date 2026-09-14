package com.example.shelfkeeper.adapter.web.book;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.shelfkeeper.adapter.web.book.create.BookCreateRequest;
import com.example.shelfkeeper.adapter.web.book.list.BookListPresenter;
import com.example.shelfkeeper.adapter.web.book.list.BookListResponse;
import com.example.shelfkeeper.adapter.web.book.search.BookSearchPresenter;
import com.example.shelfkeeper.adapter.web.book.search.BookSearchRequest;
import com.example.shelfkeeper.adapter.web.book.search.BookSearchResponse;
import com.example.shelfkeeper.usecase.book.create.BookCreateUseCase;
import com.example.shelfkeeper.usecase.book.delete.BookDeleteInputData;
import com.example.shelfkeeper.usecase.book.delete.BookDeleteUseCase;
import com.example.shelfkeeper.usecase.book.list.BookListUseCase;
import com.example.shelfkeeper.usecase.book.search.BookSearchUseCase;

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
  /** 蔵書一覧取得ユースケース。 */
  private final BookListUseCase bookListUseCase;
  /** 蔵書一覧取得のプレゼンター。 */
  private final BookListPresenter bookListPresenter;
  /** 蔵書検索ユースケース。 */
  private final BookSearchUseCase bookSearchUseCase;
  /** 蔵書検索のプレゼンター。 */
  private final BookSearchPresenter bookSearchPresenter;

  public BookController(BookCreateUseCase bookCreateUseCase, BookDeleteUseCase bookDeleteUseCase,
      BookListUseCase bookListUseCase, BookListPresenter bookListPresenter, BookSearchUseCase bookSearchUseCase,
      BookSearchPresenter bookSearchPresenter) {
    this.bookCreateUseCase = bookCreateUseCase;
    this.bookDeleteUseCase = bookDeleteUseCase;
    this.bookListUseCase = bookListUseCase;
    this.bookListPresenter = bookListPresenter;
    this.bookSearchUseCase = bookSearchUseCase;
    this.bookSearchPresenter = bookSearchPresenter;
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

  /**
   * 蔵書一覧を取得する。
   *
   * @return 蔵書一覧
   */
  @GetMapping("")
  public ResponseEntity<BookListResponse> list() {
    bookListUseCase.handle();
    return ResponseEntity.ok(bookListPresenter.getResponse());
  }

  /**
   * タイトルで蔵書を検索する。
   *
   * @param request 検索リクエスト
   * @return 検索結果の蔵書一覧
   */
  @GetMapping("search")
  public ResponseEntity<BookSearchResponse> search(@ModelAttribute @Valid BookSearchRequest request) {
    bookSearchUseCase.handle(request.toBookSearchInputData());
    return ResponseEntity.ok(bookSearchPresenter.getResponse());
  }
}
