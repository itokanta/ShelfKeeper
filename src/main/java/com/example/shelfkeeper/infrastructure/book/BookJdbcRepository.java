package com.example.shelfkeeper.infrastructure.book;

import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.shelfkeeper.domain.book.Book;
import com.example.shelfkeeper.usecase.port.BookRepository;
import com.example.shelfkeeper.usecase.port.entity.book.BookStatus;

@Repository
public class BookJdbcRepository implements BookRepository {
  private final NamedParameterJdbcTemplate template;

  public BookJdbcRepository(NamedParameterJdbcTemplate template) {
    this.template = template;
  }

  @Override
  public void bookCreate(Book book) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void bookDelete(Integer id) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void bookUpdate(Book book) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public List<BookStatus> findAll() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Optional<Book> findById(Integer id) {
    // TODO Auto-generated method stub
    return Optional.empty();
  }

  @Override
  public List<BookStatus> findByTitle(String title) {
    // TODO Auto-generated method stub
    return null;
  }
}
