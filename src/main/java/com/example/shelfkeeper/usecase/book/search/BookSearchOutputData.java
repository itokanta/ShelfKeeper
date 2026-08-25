package com.example.shelfkeeper.usecase.book.search;

import java.util.List;

/**
 * 蔵書検索ユースケースの出力データ。
 *
 * @author itokanta
 */
public class BookSearchOutputData {
  /** 検索結果の蔵書一覧。 */
  private final List<BookSearchItemOutputData> outputData;

  public BookSearchOutputData(List<BookSearchItemOutputData> outputData) {
    this.outputData = outputData;
  }

  public List<BookSearchItemOutputData> getOutputData() {
    return outputData;
  }
}
