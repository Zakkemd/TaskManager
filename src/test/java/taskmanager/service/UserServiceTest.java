package taskmanager.service;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.mockito.Mockito;
import taskmanager.model.User;
import taskmanager.repository.UserRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class UserServiceTest {

    private final UserRepository userRepository = Mockito.mock(UserRepository.class);
    private final UserService userService = new UserService(userRepository);

    private User createUser(String username, String password, String email) {
        return new User(username, password, email);
    }

    @Test
    public void shouldCreateUserSuccessfully() {
        // given
        User userToCreate = createUser("testUsername", "testPassword", "test@email.com");
        Mockito.when(userRepository.save(userToCreate)).thenReturn(userToCreate);

        // when
        User createdUser = userService.addUser(userToCreate);

        // then
        Assertions.assertThat(createdUser).isEqualTo(userToCreate);
    }

    @Test
    public void shouldGetUserById() {
        // given
        User user = createUser("testUsername", "testPassword", "test@email.com");
        user.setId(1L);
        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        // when
        User fetchedUser = userService.getUserByID(1L);

        // then
        Assertions.assertThat(fetchedUser).isEqualTo(user);
    }

    @Test
    public void shouldUpdateUser() {
        // given
        User user = createUser("testUsername", "testPassword", "test@email.com");
        user.setId(1L);

        User updatedUser = createUser("updatedUsername", "updatedPassword", "updated@email.com");
        updatedUser.setId(1L);
        Mockito.when(userRepository.save(user)).thenReturn(updatedUser);

        // when
        userService.updateUser(user);

        // then
        Mockito.verify(userRepository, Mockito.times(1)).save(user);
    }

    @Test
    public void shouldRemoveUser() {
        // given
        User user = createUser("testUsername", "testPassword", "test@email.com");
        user.setId(1L);
        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        // when
        userService.removeUser(1L);

        // then
        Mockito.verify(userRepository, Mockito.times(1)).deleteUserById(1L);
    }

    @Test
    public void shouldGetAllUsers() {
        // given
        User user1 = createUser("testUsername1", "testPassword1", "test1@email.com");
        User user2 = createUser("testUsername2", "testPassword2", "test2@email.com");
        List<User> users = Arrays.asList(user1, user2);
        Mockito.when(userRepository.findAll()).thenReturn(users);

        // when
        List<User> fetchedUsers = userService.getAllUsers();

        // then
        Assertions.assertThat(fetchedUsers).isEqualTo(users);
    }

    @Test
    public void shouldGetUserByUsername() {
        // given
        User user = createUser("testUsername", "testPassword", "test@email.com");
        Mockito.when(userRepository.findByUsername("testUsername")).thenReturn(user);

        // when
        User fetchedUser = userService.getUserByUsername("testUsername");

        // then
        Assertions.assertThat(fetchedUser).isEqualTo(user);
    }
}
