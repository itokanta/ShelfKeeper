package com.example.shelfkeeper.usecase.book.list;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.shelfkeeper.usecase.port.BookRepository;
import com.example.shelfkeeper.usecase.port.entity.book.BookStatus;

/**
 * {@link BookListInteractor} のテストクラス。
 *
 * @author itokanta
 */
@ExtendWith(MockitoExtension.class)
public class BookListInteractorTest {

  @Mock
  private BookRepository bookRepository;

  @Mock
  private BookListOutputBoundary bookListOutputBoundary;

  @InjectMocks
  private BookListInteractor bookListInteractor;

  /**
   * 蔵書を全件取得し、出力境界へ引き渡すことを検証する。
   */
  @Test
  void handleSuccess() {
    List<BookStatus> findAllResult = new ArrayList<>();
    findAllResult.add(new BookStatus(1, "test1", "testName1", true));
    findAllResult.add(new BookStatus(2, "test2", "testName2", false));

    when(bookRepository.findAll()).thenReturn(findAllResult);

    bookListInteractor.handle();

    verify(bookRepository).findAll();
    ArgumentCaptor<BookListOutputData> captor = ArgumentCaptor.forClass(BookListOutputData.class);
    verify(bookListOutputBoundary).complete(captor.capture());

    BookListOutputData outputData = captor.getValue();
    List<BookListItemOutputData> itemList = outputData.getOutputData();
    assertEquals(2, itemList.size());
    for (int i = 0; i < itemList.size(); i++) {
      BookStatus bookStatus = findAllResult.get(i);
      BookListItemOutputData outputItem = itemList.get(i);
      assertEquals(bookStatus.getId(), outputItem.getBookId());
      assertEquals(bookStatus.getTitle(), outputItem.getTitle());
      assertEquals(bookStatus.getAuthorName(), outputItem.getAuthorName());
      assertEquals(bookStatus.getStatus(), outputItem.getStatus());
    }
  }
}
