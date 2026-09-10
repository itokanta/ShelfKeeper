package com.example.shelfkeeper.infrastructure.user;

import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.shelfkeeper.domain.user.User;
import com.example.shelfkeeper.usecase.port.UserRepository;
import com.example.shelfkeeper.usecase.port.entity.user.UserOverDue;

@Repository
public class UserJdbcRepository implements UserRepository {
  private final NamedParameterJdbcTemplate template;

  public UserJdbcRepository(NamedParameterJdbcTemplate template) {
    this.template = template;
  }

  @Override
  public List<User> findAll() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Optional<User> findById(Integer id) {
    // TODO Auto-generated method stub
    return Optional.empty();
  }

  @Override
  public List<UserOverDue> findOverDueUser() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void userCreate(User user) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void userDelete(Integer id) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void userUpdate(User user) {
    // TODO Auto-generated method stub
    
  }
}
