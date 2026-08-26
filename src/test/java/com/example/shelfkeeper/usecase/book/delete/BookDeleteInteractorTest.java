package com.example.shelfkeeper.usecase.book.delete;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.shelfkeeper.domain.book.Book;
import com.example.shelfkeeper.usecase.port.BookRepository;

/**
 * {@link BookDeleteInteractor} のテストクラス。
 *
 * @author itokanta
 */
@ExtendWith(MockitoExtension.class)
public class BookDeleteInteractorTest {

  @Mock
  private BookRepository bookRepository;

  @InjectMocks
  private BookDeleteInteractor bookDeleteInteractor;

  /**
   * 存在する蔵書を削除できることを検証する。
   */
  @Test
  void handleSuccess() {
    BookDeleteInputData deleteTarget = new BookDeleteInputData(1);

    when(bookRepository.findById(deleteTarget.getId())).thenReturn(Optional.of(new Book(1, "test", "testName")));
    
    bookDeleteInteractor.handle(deleteTarget);

    verify(bookRepository).findById(deleteTarget.getId());
    verify(bookRepository).bookDelete(deleteTarget.getId());
  }

  /**
   * 存在しない蔵書を削除しようとした場合、例外をスローし削除しないことを検証する。
   */
  @Test
  void handleError() {
    BookDeleteInputData deleteTarget = new BookDeleteInputData(1);

    when(bookRepository.findById(deleteTarget.getId())).thenReturn(Optional.empty());

    assertThrows(RuntimeException.class, () -> bookDeleteInteractor.handle(deleteTarget));
    verify(bookRepository).findById(deleteTarget.getId());
    verify(bookRepository, never()).bookDelete(any());
  }
}
