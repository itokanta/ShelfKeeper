package com.example.shelfkeeper.adapter.web.user.overduelist;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import com.example.shelfkeeper.usecase.user.overduelist.UserOverDueListDueBookItem;
import com.example.shelfkeeper.usecase.user.overduelist.UserOverDueListItemOutputData;
import com.example.shelfkeeper.usecase.user.overduelist.UserOverDueListOutputBoundary;
import com.example.shelfkeeper.usecase.user.overduelist.UserOverDueListOutputData;

/**
 * {@link UserOverDueListOutputBoundary} の実装クラス。
 * ユースケースの出力データを API レスポンスへ変換する。
 *
 * @author itokanta
 */
@Component
@RequestScope
public class UserOverDueListPresenter implements UserOverDueListOutputBoundary {
  /** API レスポンス。 */
  private UserOverDueListResponse response;

  /**
   * 取得結果をレスポンスへ変換する。
   *
   * @param userOverDueListOutputData 取得した延滞利用者一覧の出力データ
   */
  @Override
  public void complete(UserOverDueListOutputData userOverDueListOutputData) {
    List<UserOverDueListItem> userOverDueList = new ArrayList<>();

    for(UserOverDueListItemOutputData outputData : userOverDueListOutputData.getOutputDataList()) {
      List<DueBookListItem> overDueBookList = new ArrayList<>();
      for(UserOverDueListDueBookItem dueBookItem : outputData.getDueBookList()) {
        overDueBookList.add(new DueBookListItem(dueBookItem.getBookId(), dueBookItem.getBookTitle(), dueBookItem.getLoanDate(), dueBookItem.getDueDate()));
      }
      userOverDueList.add(new UserOverDueListItem(outputData.getUserId(), outputData.getUserName(), overDueBookList));
    }

    this.response = new UserOverDueListResponse(userOverDueList);
  }

  public UserOverDueListResponse getResponse() {
    return response;
  }
}
