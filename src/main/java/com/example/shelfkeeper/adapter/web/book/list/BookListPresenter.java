package com.example.shelfkeeper.adapter.web.book.list;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import com.example.shelfkeeper.usecase.book.list.BookListItemOutputData;
import com.example.shelfkeeper.usecase.book.list.BookListOutputBoundary;
import com.example.shelfkeeper.usecase.book.list.BookListOutputData;

/**
 * {@link BookListOutputBoundary} の実装クラス。
 * ユースケースの出力データを API レスポンスへ変換する。
 *
 * @author itokanta
 */
@Component
@RequestScope
public class BookListPresenter implements BookListOutputBoundary {
  /** API レスポンス。 */
  private BookListResponse response;

  /**
   * 取得結果をレスポンスへ変換する。
   * 貸出状態は {@code true} の場合「貸出可能」、{@code false} の場合「貸出不可」とする。
   *
   * @param bookListOutputData 取得した蔵書一覧の出力データ
   */
  @Override
  public void complete(BookListOutputData bookListOutputData) {
    List<BookListItem> responseItemList = new ArrayList<>();

    for(BookListItemOutputData outputData : bookListOutputData.getOutputData()) {
      BookListItem item = new BookListItem(
        outputData.getBookId(), 
        outputData.getTitle(),
        outputData.getAuthorName(), 
        outputData.getStatus() ? "貸出可能" : "貸出不可"
      );
      responseItemList.add(item);
    }

    this.response = new BookListResponse(responseItemList);
  }

  public BookListResponse getResponse() {
    return response;
  }
}
