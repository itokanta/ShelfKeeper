package com.example.shelfkeeper.usecase.book.delete;

import java.util.Optional;

import com.example.shelfkeeper.adapter.web.errorresponse.BadRequestException;
import com.example.shelfkeeper.domain.book.Book;
import com.example.shelfkeeper.usecase.port.BookRepository;

/**
 * {@link BookDeleteUseCase} の実装クラス。
 * 指定された識別子の蔵書が存在することを確認したうえで削除する。
 *
 * @author itokanta
 */
public class BookDeleteInteractor implements BookDeleteUseCase{
  /** 蔵書の永続化を担うリポジトリ。 */
  private final BookRepository bookRepository;

  public BookDeleteInteractor(BookRepository bookRepository) {
    this.bookRepository = bookRepository;
  }

  /**
   * 蔵書を削除する。
   * 指定された識別子の蔵書が存在しない場合は例外をスローする。
   *
   * @param bookDeleteInputData 削除する蔵書の入力データ
   * @throws BadRequestException 指定された蔵書が存在しない場合
   */
  @Override
  public void handle(BookDeleteInputData bookDeleteInputData) {
    Integer deleteTargetId = bookDeleteInputData.getId();
    Optional<Book> deleteTarget = bookRepository.findById(deleteTargetId);

    if(deleteTarget.isEmpty()) {
      throw new BadRequestException("指定された書籍は登録されていません");
    }

    bookRepository.bookDelete(deleteTargetId);
  }
}
