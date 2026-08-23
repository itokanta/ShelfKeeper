package com.example.shelfkeeper.usecase.user.delete;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.shelfkeeper.domain.user.User;
import com.example.shelfkeeper.usecase.port.UserRepository;

/**
 * {@link UserDeleteInteractor} のテストクラス。
 *
 * @author itokanta
 */
@ExtendWith(MockitoExtension.class)
public class UserDeleteInteractorTest {

  @Mock
  private UserRepository userRepository;

  @InjectMocks
  private UserDeleteInteractor useDeleteInteractor;

  /**
   * 存在する利用者を削除できることを検証する。
   */
  @Test
  void handleSuccess() {
    UserDeleteInputData deleteTarget = new UserDeleteInputData(1);

    when(userRepository.findById(deleteTarget.getId())).thenReturn(Optional.of(new User(1, "test", null, null)));

    useDeleteInteractor.handle(deleteTarget);

    verify(userRepository).findById(deleteTarget.getId());
    verify(userRepository).userDelete(deleteTarget.getId());
  }

  /**
   * 存在しない利用者を削除しようとした場合、例外をスローし削除しないことを検証する。
   */
  @Test
  void handleError() {
    UserDeleteInputData deleteTarget = new UserDeleteInputData(1);

    when(userRepository.findById(deleteTarget.getId())).thenReturn(Optional.empty());

    assertThrows(RuntimeException.class, () -> useDeleteInteractor.handle(deleteTarget));
    verify(userRepository).findById(deleteTarget.getId());
    verify(userRepository, never()).userDelete(any());
  }
}
