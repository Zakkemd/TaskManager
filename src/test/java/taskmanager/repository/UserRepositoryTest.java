package taskmanager.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import taskmanager.model.User;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserRepositoryTest {

    @Mock
    private UserRepository userRepository;

    private User user;

    @BeforeEach
    void setUp() {
        user = new User("username", "password", "email@example.com");
        user.setId(1L);
    }

    @Test
    void findByUsername() {
        when(userRepository.findByUsername("username")).thenReturn(user);

        User result = userRepository.findByUsername("username");

        assertNotNull(result);
        assertEquals(user.getId(), result.getId());
        assertEquals(user.getUsername(), result.getUsername());
        assertEquals(user.getPassword(), result.getPassword());
        assertEquals(user.getEmail(), result.getEmail());

        verify(userRepository, times(1)).findByUsername("username");
    }

    @Test
    void findById() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        Optional<User> result = userRepository.findById(1L);

        assertNotNull(result);
        assertEquals(user.getId(), result.get().getId());
        assertEquals(user.getUsername(), result.get().getUsername());
        assertEquals(user.getPassword(), result.get().getPassword());
        assertEquals(user.getEmail(), result.get().getEmail());

        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    void deleteUserById() {
        doNothing().when(userRepository).deleteById(1L);

        userRepository.deleteById(1L);

        verify(userRepository, times(1)).deleteById(1L);
    }
}
