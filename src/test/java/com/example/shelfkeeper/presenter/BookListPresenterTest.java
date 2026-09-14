package com.example.shelfkeeper.presenter;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.example.shelfkeeper.adapter.web.book.list.BookListItem;
import com.example.shelfkeeper.adapter.web.book.list.BookListPresenter;
import com.example.shelfkeeper.usecase.book.list.BookListItemOutputData;
import com.example.shelfkeeper.usecase.book.list.BookListOutputData;

/**
 * {@link BookListPresenter} のテストクラス。
 *
 * @author itokanta
 */
public class BookListPresenterTest {
  private final BookListPresenter bookListPresenter = new BookListPresenter();

  /**
   * 出力データをレスポンスへ変換し、貸出状態を表示文言に変換できることを検証する。
   */
  @Test
  void completeSuccess() {
    List<BookListItemOutputData> itemList = new ArrayList<>();
    BookListItemOutputData listItem1 = new BookListItemOutputData(1, "test1", "testName1", true);
    BookListItemOutputData listItem2 = new BookListItemOutputData(2, "test2", "testName2", false);
    itemList.add(listItem1);
    itemList.add(listItem2);
    BookListOutputData outputData = new BookListOutputData(itemList);

    bookListPresenter.complete(outputData);

    List<BookListItem> responseList = bookListPresenter.getResponse().getBookList();

    assertEquals(2, responseList.size());

    BookListItem item1 = responseList.get(0);
    assertEquals(1, item1.getBookId());
    assertEquals("test1", item1.getTitle());
    assertEquals("testName1", item1.getAuthorName());
    assertEquals("貸出可能", item1.getStatus());

    BookListItem item2 = responseList.get(1);
    assertEquals(2, item2.getBookId());
    assertEquals("test2", item2.getTitle());
    assertEquals("testName2", item2.getAuthorName());
    assertEquals("貸出不可", item2.getStatus());
  }
}
