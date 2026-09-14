package com.example.shelfkeeper.usecase.user.overduelist;

import java.util.List;

/**
 * 延滞利用者一覧取得ユースケースの出力データ。
 *
 * @author itokanta
 */
public class UserOverDueListOutputData {
  /** 延滞利用者一覧。 */
  private final List<UserOverDueListItemOutputData> outputDataList;

  public UserOverDueListOutputData(List<UserOverDueListItemOutputData> outputDataList) {
    this.outputDataList = outputDataList;
  }

  public List<UserOverDueListItemOutputData> getOutputDataList() {
    return outputDataList;
  }
}
