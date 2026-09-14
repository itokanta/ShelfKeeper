package com.example.shelfkeeper.usecase.book.create;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.shelfkeeper.domain.book.Book;
import com.example.shelfkeeper.usecase.port.BookRepository;

/**
 * {@link BookCreateInteractor} のテストクラス。
 *
 * @author itokanta
 */
@ExtendWith(MockitoExtension.class)
public class BookCreateInteractorTest {

  @Mock
  private BookRepository bookRepository;

  @InjectMocks
  private BookCreateInteractor bookCreateInteractor;

  /**
   * 指定したタイトルと著者名で蔵書を作成できることを検証する。
   */
  @Test
  void handleSuccess() {
    BookCreateInputData newBook = new BookCreateInputData("test", "testName");

    bookCreateInteractor.handle(newBook);

    ArgumentCaptor<Book> captor = ArgumentCaptor.forClass(Book.class);
    verify(bookRepository).bookCreate(captor.capture());

    Book created = captor.getValue();
    assertEquals(newBook.getTitle(), created.getTitle());
    assertEquals(newBook.getAuthorName(), created.getAuthorName());
  }
}
