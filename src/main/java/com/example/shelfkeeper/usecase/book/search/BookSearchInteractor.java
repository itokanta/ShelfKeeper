package com.example.shelfkeeper.usecase.book.search;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.shelfkeeper.usecase.port.BookRepository;
import com.example.shelfkeeper.usecase.port.entity.book.BookStatus;

/**
 * {@link BookSearchUseCase} の実装クラス。
 * タイトルで蔵書を検索し、出力境界へ引き渡す。
 *
 * @author itokanta
 */
@Service
public class BookSearchInteractor implements BookSearchUseCase {
  /** 蔵書の永続化を担うリポジトリ。 */
  private final BookRepository bookRepository;
  /** 検索結果を後続処理へ引き渡す出力境界。 */
  private final BookSearchOutputBoundary bookSearchOutputBoundary;

  public BookSearchInteractor(BookRepository bookRepository, BookSearchOutputBoundary bookSearchOutputBoundary) {
    this.bookRepository = bookRepository;
    this.bookSearchOutputBoundary = bookSearchOutputBoundary;
  }

  /**
   * 蔵書をタイトルで検索する。
   * 該当する蔵書がない場合は空の一覧を出力境界へ引き渡す。
   *
   * @param bookSearchInputData 検索する蔵書の入力データ
   */
  @Override
  public void handle(BookSearchInputData bookSearchInputData) {
    List<BookStatus> bookList = bookRepository.findByTitle(bookSearchInputData.getTitle());
    List<BookSearchItemOutputData> outputDataList = new ArrayList<>();

    for (BookStatus bookStatus : bookList) {
      outputDataList.add(new BookSearchItemOutputData(
          bookStatus.getId(),
          bookStatus.getTitle(),
          bookStatus.getAuthorName(),
          bookStatus.getStatus()));
    }

    bookSearchOutputBoundary.complete(new BookSearchOutputData(outputDataList));
  }
}
