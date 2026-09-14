package com.example.shelfkeeper.adapter.web.user.list;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import com.example.shelfkeeper.usecase.user.list.UserListItemOutputData;
import com.example.shelfkeeper.usecase.user.list.UserListOutputBoundary;
import com.example.shelfkeeper.usecase.user.list.UserListOutputData;

/**
 * {@link UserListOutputBoundary} の実装クラス。
 * ユースケースの出力データを API レスポンスへ変換する。
 *
 * @author itokanta
 */
@Component
@RequestScope
public class UserListPresenter implements UserListOutputBoundary {
  /** API レスポンス。 */
  private UserListResponse response;

  /**
   * 取得結果をレスポンスへ変換する。
   *
   * @param userListOutputData 取得した利用者一覧の出力データ
   */
  @Override
  public void complete(UserListOutputData userListOutputData) {
    List<UserListItem> userList = new ArrayList<>();

    for (UserListItemOutputData outputData : userListOutputData.getUserList()) {
      userList.add(new UserListItem(outputData.getId(), outputData.getName()));
    }

    this.response = new UserListResponse(userList);
  }

  public UserListResponse getResponse() {
    return response;
  }
}
