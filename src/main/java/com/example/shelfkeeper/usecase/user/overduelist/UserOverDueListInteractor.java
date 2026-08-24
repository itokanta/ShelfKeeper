package com.example.shelfkeeper.usecase.user.overduelist;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.shelfkeeper.infrastructure.entity.user.UserOverDue;
import com.example.shelfkeeper.infrastructure.entity.user.UserOverDueBook;
import com.example.shelfkeeper.usecase.port.UserRepository;

/**
 * {@link UserOverDueListUseCase} の実装クラス。
 * 延滞中の利用者とその延滞書籍一覧を取得し、出力境界へ引き渡す。
 *
 * @author itokanta
 */
@Service
public class UserOverDueListInteractor implements UserOverDueListUseCase{
  /** 利用者の永続化を担うリポジトリ。 */
  private final UserRepository userRepository;
  /** 取得結果を後続処理へ引き渡す出力境界。 */
  private final UserOverDueListOutputBoundary userOverDueListOutputBoundary;

  public UserOverDueListInteractor(UserRepository userRepository,
      UserOverDueListOutputBoundary userOverDueListOutputBoundary) {
    this.userRepository = userRepository;
    this.userOverDueListOutputBoundary = userOverDueListOutputBoundary;
  }

  /**
   * 延滞中の利用者一覧を取得する。
   */
  @Override
  public void handle() {
    List<UserOverDue> userOverDueList = userRepository.findOverDueUser();
    List<UserOverDueListItemOutputData> outputDataList = new ArrayList<>();

    for(UserOverDue userOverDue : userOverDueList) {
      List<UserOverDueListDueBookItem> bookList = new ArrayList<>();
      for(UserOverDueBook userOverDueBook : userOverDue.getDueBookList()){
        UserOverDueListDueBookItem dueBook = new UserOverDueListDueBookItem(
          userOverDueBook.getBookTitle(),
          userOverDueBook.getLoanDate(),
          userOverDueBook.getDueDate()
        );
        bookList.add(dueBook);
      }

      UserOverDueListItemOutputData outputData = new UserOverDueListItemOutputData(
        userOverDue.getUserId(),
        userOverDue.getUserName(),
        bookList
      );
      outputDataList.add(outputData);
    }

    userOverDueListOutputBoundary.complete(new UserOverDueListOutputData(outputDataList));
  }
}
