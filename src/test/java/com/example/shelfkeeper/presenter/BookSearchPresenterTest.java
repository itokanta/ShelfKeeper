package com.example.shelfkeeper.presenter;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.example.shelfkeeper.adapter.web.book.search.BookSearchItem;
import com.example.shelfkeeper.adapter.web.book.search.BookSearchPresenter;
import com.example.shelfkeeper.usecase.book.search.BookSearchItemOutputData;
import com.example.shelfkeeper.usecase.book.search.BookSearchOutputData;

/**
 * {@link BookSearchPresenter} のテストクラス。
 *
 * @author itokanta
 */
public class BookSearchPresenterTest {
  private final BookSearchPresenter bookSearchPresenter = new BookSearchPresenter();

  /**
   * 出力データをレスポンスへ変換し、貸出状態を表示文言に変換できることを検証する。
   */
  @Test
  void completeSuccess() {
    List<BookSearchItemOutputData> itemList = new ArrayList<>();
    BookSearchItemOutputData listItem1 = new BookSearchItemOutputData(1, "test1", "testName1", true);
    BookSearchItemOutputData listItem2 = new BookSearchItemOutputData(2, "test2", "testName2", false);
    itemList.add(listItem1);
    itemList.add(listItem2);
    BookSearchOutputData outputData = new BookSearchOutputData(itemList);

    bookSearchPresenter.complete(outputData);

    List<BookSearchItem> responseList = bookSearchPresenter.getResponse().getSearchBookList();

    assertEquals(2, responseList.size());

    BookSearchItem item1 = responseList.get(0);
    assertEquals(1, item1.getBookId());
    assertEquals("test1", item1.getTitle());
    assertEquals("testName1", item1.getAuthorName());
    assertEquals("貸出可能", item1.getStatus());

    BookSearchItem item2 = responseList.get(1);
    assertEquals(2, item2.getBookId());
    assertEquals("test2", item2.getTitle());
    assertEquals("testName2", item2.getAuthorName());
    assertEquals("貸出不可", item2.getStatus());
  }
}
