package com.example.shelfkeeper.usecase.port;

import java.util.List;
import java.util.Optional;

import com.example.shelfkeeper.domain.book.Book;
import com.example.shelfkeeper.infrastructure.entity.book.BookStatus;

/**
 * 蔵書の永続化を担うリポジトリ。
 *
 * @author itokanta
 */
public interface BookRepository {
  /**
   * 識別子で蔵書を取得する。
   *
   * @param id 書籍の識別子
   * @return 該当する蔵書。存在しない場合は空
   */
  Optional<Book> findById(Integer id);

  /**
   * 蔵書を貸出状態付きで全件取得する。
   *
   * @return 蔵書と貸出状態の一覧
   */
  List<BookStatus> findAll();

  /**
   * タイトルで蔵書を貸出状態付きで取得する。
   *
   * @param title 書籍のタイトル
   * @return 該当する蔵書と貸出状態の一覧。存在しない場合は空
   */
  Optional<List<BookStatus>> findByTitle(String title);

  /**
   * 蔵書を登録する。
   *
   * @param book 登録する蔵書
   */
  void bookCreate(Book book);

  /**
   * 蔵書を更新する。
   *
   * @param book 更新する蔵書
   */
  void bookUpdate(Book book);

  /**
   * 蔵書を削除する。
   *
   * @param id 削除する書籍の識別子
   */
  void bookDelete(Integer id);
}