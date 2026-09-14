package com.example.shelfkeeper.usecase.user.list;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.shelfkeeper.domain.user.User;
import com.example.shelfkeeper.usecase.port.UserRepository;

/**
 * {@link UserListInteractor} のテストクラス。
 *
 * @author itokanta
 */
@ExtendWith(MockitoExtension.class)
public class UserListInteractorTest {

  @Mock
  private UserRepository userRepository;

  @Mock
  private UserListOutputBoundary userListOutputBoundary;

  @InjectMocks
  private UserListInteractor userListInteractor;

  /**
   * 利用者を全件取得し、出力境界へ引き渡すことを検証する。
   */
  @Test
  void handleSuccess() {
    List<User> findAllResult = new ArrayList<>();
    findAllResult.add(new User(1, "test"));
    findAllResult.add(new User(2, "test2"));

    when(userRepository.findAll()).thenReturn(findAllResult);

    userListInteractor.handle();

    verify(userRepository).findAll();
    ArgumentCaptor<UserListOutputData> captor = ArgumentCaptor.forClass(UserListOutputData.class);
    verify(userListOutputBoundary).complete(captor.capture());

    UserListOutputData outputData = captor.getValue();
    List<UserListItemOutputData> itemList = outputData.getUserList();
    assertEquals(2, itemList.size());
    for (int i = 0; i < itemList.size(); i++) {
      assertEquals(findAllResult.get(i).getId(), itemList.get(i).getId());
      assertEquals(findAllResult.get(i).getName(), itemList.get(i).getName());
    }
  }
}
