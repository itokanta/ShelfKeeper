package com.example.shelfkeeper.adapter.web.book.search;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import com.example.shelfkeeper.usecase.book.search.BookSearchItemOutputData;
import com.example.shelfkeeper.usecase.book.search.BookSearchOutputBoundary;
import com.example.shelfkeeper.usecase.book.search.BookSearchOutputData;

/**
 * {@link BookSearchOutputBoundary} の実装クラス。
 * ユースケースの出力データを API レスポンスへ変換する。
 *
 * @author itokanta
 */
@Component
@RequestScope
public class BookSearchPresenter implements BookSearchOutputBoundary {
  /** API レスポンス。 */
  private BookSearchResponse response;

  /**
   * 検索結果をレスポンスへ変換する。
   * 貸出状態は {@code true} の場合「貸出可能」、{@code false} の場合「貸出不可」とする。
   *
   * @param bookSearchOutputData 検索した蔵書一覧の出力データ
   */
  @Override
  public void complete(BookSearchOutputData bookSearchOutputData) {
    List<BookSearchItem> responseItemList = new ArrayList<>();

    for (BookSearchItemOutputData outputData : bookSearchOutputData.getOutputData()) {
      BookSearchItem item = new BookSearchItem(
          outputData.getBookId(),
          outputData.getTitle(),
          outputData.getAuthorName(),
          outputData.getStatus() ? "貸出可能" : "貸出不可");
      responseItemList.add(item);
    }

    this.response = new BookSearchResponse(responseItemList);
  }

  public BookSearchResponse getResponse() {
    return response;
  }
}
