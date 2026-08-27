package com.example.shelfkeeper.usecase.book.search;

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

import com.example.shelfkeeper.infrastructure.entity.book.BookStatus;
import com.example.shelfkeeper.usecase.port.BookRepository;

/**
 * {@link BookSearchInteractor} のテストクラス。
 *
 * @author itokanta
 */
@ExtendWith(MockitoExtension.class)
public class BookSearchInteractorTest {

  @Mock
  private BookRepository bookRepository;

  @Mock
  private BookSearchOutputBoundary bookSearchOutputBoundary;

  @InjectMocks
  private BookSearchInteractor bookSearchInteractor;

  /**
   * タイトルで蔵書を検索し、出力境界へ引き渡すことを検証する。
   */
  @Test
  void handleSuccess() {
    BookSearchInputData searchTitle = new BookSearchInputData("test");
    List<BookStatus> findByTitleResult = new ArrayList<>();
    findByTitleResult.add(new BookStatus(1, "test1", "testName1", true));
    findByTitleResult.add(new BookStatus(2, "test2", "testName2", false));

    when(bookRepository.findByTitle(searchTitle.getTitle())).thenReturn(findByTitleResult);

    bookSearchInteractor.handle(searchTitle);

    verify(bookRepository).findByTitle(searchTitle.getTitle());
    ArgumentCaptor<BookSearchOutputData> captor = ArgumentCaptor.forClass(BookSearchOutputData.class);
    verify(bookSearchOutputBoundary).complete(captor.capture());

    BookSearchOutputData outputData = captor.getValue();
    List<BookSearchItemOutputData> itemList = outputData.getOutputData();
    assertEquals(2, itemList.size());
    for(int i=0; i < itemList.size(); i++){
      BookStatus bookStatus = findByTitleResult.get(i);
      BookSearchItemOutputData item = itemList.get(i);
      assertEquals(bookStatus.getId(), item.getBookId());
      assertEquals(bookStatus.getTitle(), item.getTitle());
      assertEquals(bookStatus.getAuthorName(), item.getAuthorName());
      assertEquals(bookStatus.getStatus(), item.getStatus());
    }
  }
}
