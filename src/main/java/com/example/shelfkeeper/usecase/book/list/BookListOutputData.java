package com.example.shelfkeeper.usecase.book.list;

import java.util.List;

/**
 * 蔵書一覧取得ユースケースの出力データ。
 *
 * @author itokanta
 */
public class BookListOutputData {
  /** 蔵書一覧。 */
  private final List<BookListItemOutputData> outputData;

  public BookListOutputData(List<BookListItemOutputData> outputData) {
    this.outputData = outputData;
  }

  public List<BookListItemOutputData> getOutputData() {
    return outputData;
  }
}
