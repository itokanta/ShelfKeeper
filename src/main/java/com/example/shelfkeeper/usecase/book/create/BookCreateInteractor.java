package com.example.shelfkeeper.usecase.book.create;

import org.springframework.stereotype.Service;

import com.example.shelfkeeper.domain.book.Book;
import com.example.shelfkeeper.usecase.port.BookRepository;

/**
 * {@link BookCreateUseCase} の実装クラス。
 * 入力されたタイトルと著者名で蔵書を登録する。
 *
 * @author itokanta
 */
@Service
public class BookCreateInteractor implements BookCreateUseCase{
  /** 蔵書の永続化を担うリポジトリ。 */
  private final BookRepository bookRepository;

  public BookCreateInteractor(BookRepository bookRepository) {
    this.bookRepository = bookRepository;
  }

  /**
   * 蔵書を作成する。
   *
   * @param bookCreateInputData 作成する蔵書の入力データ
   */
  @Override
  public void handle(BookCreateInputData bookCreateInputData) {
    bookRepository.bookCreate(new Book(null, bookCreateInputData.getTitle(), bookCreateInputData.getAuthorName()));
  }
}
