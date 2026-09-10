package com.example.shelfkeeper.infrastructure.adminuser;

import java.util.Optional;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.shelfkeeper.domain.adminuser.AdminUser;
import com.example.shelfkeeper.usecase.port.AdminUserRepository;

@Repository
public class AdminUserJdbcRepository implements AdminUserRepository{
  private final NamedParameterJdbcTemplate template;

  public AdminUserJdbcRepository(NamedParameterJdbcTemplate template) {
    this.template = template;
  }

  @Override
  public void adminUserCreate(AdminUser adminUser) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void adminUserDelete(Integer id) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void adminUserUpdate(AdminUser adminUser) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public AdminUser findById(Integer id) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Optional<AdminUser> findByMail(String mail) {
    // TODO Auto-generated method stub
    return Optional.empty();
  }
}
