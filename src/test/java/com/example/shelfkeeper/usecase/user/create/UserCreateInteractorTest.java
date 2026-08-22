package com.example.shelfkeeper.usecase.user.create;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.shelfkeeper.domain.user.User;
import com.example.shelfkeeper.usecase.port.UserRepository;

/**
 * {@link UserCreateInteractor} のテストクラス。
 *
 * @author itokanta
 */
@ExtendWith(MockitoExtension.class)
public class UserCreateInteractorTest {

  @Mock
  private UserRepository userRepository;

  @InjectMocks
  private UserCreateInteractor userCreateInteractor;

  /**
   * 指定した名前で利用者を作成できることを検証する。
   */
  @Test
  void handleSuccess() {
    UserCreateInputData newUser = new UserCreateInputData("test");

    userCreateInteractor.handle(newUser);

    ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
    verify(userRepository).userCreate(captor.capture());

    User created = captor.getValue();
    assertEquals(newUser.getName(), created.getName());
  }
}
