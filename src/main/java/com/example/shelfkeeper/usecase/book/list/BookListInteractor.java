package com.example.shelfkeeper.usecase.book.list;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.shelfkeeper.infrastructure.entity.book.BookStatus;
import com.example.shelfkeeper.usecase.port.BookRepository;

/**
 * {@link BookListUseCase} の実装クラス。
 * 蔵書を貸出状態付きで全件取得し、出力境界へ引き渡す。
 *
 * @author itokanta
 */
@Service
public class BookListInteractor implements BookListUseCase{
  /** 蔵書の永続化を担うリポジトリ。 */
  private final BookRepository bookRepository;
  /** 取得結果を後続処理へ引き渡す出力境界。 */
  private final BookListOutputBoundary bookListOutputBoundary;

  public BookListInteractor(BookRepository bookRepository, BookListOutputBoundary bookListOutputBoundary) {
    this.bookRepository = bookRepository;
    this.bookListOutputBoundary = bookListOutputBoundary;
  }

  /**
   * 蔵書を全件取得する。
   */
  @Override
  public void handle() {
    List<BookStatus> findAllResult = bookRepository.findAll();
    List<BookListItemOutputData> outputDataList = new ArrayList<>();

    for(BookStatus bookStatus : findAllResult) {
      outputDataList.add(new BookListItemOutputData(
        bookStatus.getTitle(),
        bookStatus.getAuthorName(),
        bookStatus.getStatus()
      ));
    }

    bookListOutputBoundary.complete(new BookListOutputData(outputDataList));
  }
}
