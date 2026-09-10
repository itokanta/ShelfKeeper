package com.example.shelfkeeper.infrastructure.loanrecord;

import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.shelfkeeper.domain.loanrecord.LoanRecord;
import com.example.shelfkeeper.usecase.port.LoanRecordRepository;
import com.example.shelfkeeper.usecase.port.entity.loanrecord.BookHistory;

@Repository
public class LoanRecordJdbcRepository implements LoanRecordRepository {
  private final NamedParameterJdbcTemplate template;

  public LoanRecordJdbcRepository(NamedParameterJdbcTemplate template) {
    this.template = template;
  }

  @Override
  public void checkIn(LoanRecord loanRecord) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void checkOut(LoanRecord loanRecord) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public List<BookHistory> findByBookId(Integer bookId) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Optional<LoanRecord> findById(Integer id) {
    // TODO Auto-generated method stub
    return Optional.empty();
  }

  @Override
  public Optional<LoanRecord> returnDateIsNullFindByBookId(Integer bookId) {
    // TODO Auto-generated method stub
    return Optional.empty();
  }
}
