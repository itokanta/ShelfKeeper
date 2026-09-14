package com.example.shelfkeeper.adapter.web.adminuser.findsingle;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import com.example.shelfkeeper.usecase.adminuser.findsingle.AdminUserFindSingleOutputBoundary;
import com.example.shelfkeeper.usecase.adminuser.findsingle.AdminUserFindSingleOutputData;

/**
 * {@link AdminUserFindSingleOutputBoundary} の実装クラス。
 * ユースケースの出力データを API レスポンスへ変換する。
 *
 * @author itokanta
 */
@Component 
@RequestScope 
public class AdminUserFindSinglePresenter implements AdminUserFindSingleOutputBoundary {
  /** API レスポンス。 */
  private AdminUserFindSingleResponse response;

  /**
   * 取得結果をレスポンスへ変換する。
   *
   * @param adminUserFindSingleOutputData 取得した管理者ユーザーの出力データ
   */
  @Override
  public void complete(AdminUserFindSingleOutputData adminUserFindSingleOutputData) {
    this.response = new AdminUserFindSingleResponse(adminUserFindSingleOutputData.getName(), adminUserFindSingleOutputData.getMail());
  }

  public AdminUserFindSingleResponse getResponse() {
    return response;
  }
}
