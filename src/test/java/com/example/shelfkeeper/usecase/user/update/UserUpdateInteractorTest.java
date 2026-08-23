package com.example.shelfkeeper.usecase.user.update;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.shelfkeeper.domain.user.User;
import com.example.shelfkeeper.usecase.port.UserRepository;

/**
 * {@link UserUpdateInteractor} のテストクラス。
 *
 * @author itokanta
 */
@ExtendWith(MockitoExtension.class)
public class UserUpdateInteractorTest {

  @Mock
  private UserRepository userRepository;

  @InjectMocks
  private UserUpdateInteractor userUpdateInteractor;

  /**
   * 利用者名を更新できることを検証する。
   */
  @Test
  void handleSuccessAllUpdate() {
    UserUpdateInputData inputData = new UserUpdateInputData(1, "test2");
    User dbData = new User(1, "test", null, null);

    when(userRepository.findById(inputData.getId())).thenReturn(Optional.of(dbData));

    userUpdateInteractor.handle(inputData);

    verify(userRepository).findById(inputData.getId());
    ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
    verify(userRepository).userUpdate(captor.capture());

    User updated = captor.getValue();
    assertEquals(inputData.getName(), updated.getName());
  }

  /**
   * 名前が {@code null} の場合、既存の値を維持して更新することを検証する。
   */
  @Test
  void handleSuccessNoUpdate() {
    UserUpdateInputData inputData = new UserUpdateInputData(1, null);
    User dbData = new User(1, "test", null, null);

    when(userRepository.findById(inputData.getId())).thenReturn(Optional.of(dbData));

    userUpdateInteractor.handle(inputData);

    verify(userRepository).findById(inputData.getId());
    ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
    verify(userRepository).userUpdate(captor.capture());

    User updated = captor.getValue();
    assertEquals("test", updated.getName());
  }

  /**
   * 存在しない利用者を更新しようとした場合、例外をスローし更新しないことを検証する。
   */
  @Test
  void handleError() {
    UserUpdateInputData inputData = new UserUpdateInputData(1, "test");

    when(userRepository.findById(inputData.getId())).thenReturn(Optional.empty());

    assertThrows(RuntimeException.class, () -> userUpdateInteractor.handle(inputData));
    verify(userRepository).findById(inputData.getId());
    verify(userRepository, never()).userUpdate(any());
  }
}
