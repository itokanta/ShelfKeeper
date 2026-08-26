package com.example.shelfkeeper.usecase.user.overduelist;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.shelfkeeper.infrastructure.entity.book.UserOverDueBook;
import com.example.shelfkeeper.infrastructure.entity.user.UserOverDue;
import com.example.shelfkeeper.usecase.port.UserRepository;

/**
 * {@link UserOverDueListInteractor} のテストクラス。
 *
 * @author itokanta
 */
@ExtendWith(MockitoExtension.class)
public class UserOverDueListInteractorTest {

  @Mock
  private UserRepository userRepository;

  @Mock
  private UserOverDueListOutputBoundary userOverDueListOutputBoundary;

  @InjectMocks
  private UserOverDueListInteractor userOverDueListInteractor;

  /**
   * 延滞中の利用者と延滞書籍一覧を取得し、出力境界へ引き渡すことを検証する。
   */
  @Test
  void handleSuccess() {
    List<UserOverDue> findOverDueUserResult = new ArrayList<>();

    List<UserOverDueBook> overDueBookList1 = new ArrayList<>(List.of(
      new UserOverDueBook(1, "testTitle1", LocalDate.of(2026, 8, 21), LocalDate.of(2026, 8, 25)), 
      new UserOverDueBook(2, "testTitle2", LocalDate.of(2026, 9, 21), LocalDate.of(2026, 9, 25))
    ));

    List<UserOverDueBook> overDueBookList2 = new ArrayList<>(List.of(
      new UserOverDueBook(3, "testTitle3", LocalDate.of(2026, 10, 21), LocalDate.of(2026, 10, 25)),
      new UserOverDueBook(4, "testTitle4", LocalDate.of(2026, 11, 21), LocalDate.of(2026, 11, 25))
    ));

    findOverDueUserResult.add(new UserOverDue(1, "test1", overDueBookList1));
    findOverDueUserResult.add(new UserOverDue(2, "test2", overDueBookList2));

    when(userRepository.findOverDueUser()).thenReturn(findOverDueUserResult);

    userOverDueListInteractor.handle();

    verify(userRepository).findOverDueUser();
    ArgumentCaptor<UserOverDueListOutputData> captor = ArgumentCaptor.forClass(UserOverDueListOutputData.class);
    verify(userOverDueListOutputBoundary).complete(captor.capture());

    UserOverDueListOutputData outputData = captor.getValue();
    List<UserOverDueListItemOutputData> itemList = outputData.getOutputDataList();
    assertEquals(2, itemList.size());
    for(int i = 0; i < itemList.size(); i++) {
      assertEquals(findOverDueUserResult.get(i).getUserId(), itemList.get(i).getUserId());
      assertEquals(findOverDueUserResult.get(i).getUserName(), itemList.get(i).getUserName());
    }

    List<UserOverDueListDueBookItem> bookList1 = itemList.get(0).getDueBookList();
    List<UserOverDueListDueBookItem> bookList2 = itemList.get(1).getDueBookList();
    assertEquals(2, bookList1.size());
    assertEquals(2, bookList2.size());
    for(int i = 0; i < bookList1.size(); i++) {
      assertEquals(overDueBookList1.get(i).getBookId(), bookList1.get(i).getBookId());
      assertEquals(overDueBookList1.get(i).getBookTitle(), bookList1.get(i).getBookTitle());
      assertEquals(overDueBookList1.get(i).getDueDate(), bookList1.get(i).getDueDate());
      assertEquals(overDueBookList1.get(i).getLoanDate(), bookList1.get(i).getLoanDate());
    }

    for (int i = 0; i < bookList2.size(); i++) {
      assertEquals(overDueBookList2.get(i).getBookId(), bookList2.get(i).getBookId());
      assertEquals(overDueBookList2.get(i).getBookTitle(), bookList2.get(i).getBookTitle());
      assertEquals(overDueBookList2.get(i).getDueDate(), bookList2.get(i).getDueDate());
      assertEquals(overDueBookList2.get(i).getLoanDate(), bookList2.get(i).getLoanDate());
    }
  }
}
