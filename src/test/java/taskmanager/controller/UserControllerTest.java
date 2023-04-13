package taskmanager.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import taskmanager.model.User;
import taskmanager.repository.UserRepository;
import taskmanager.service.UserService;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    private User user;

    @BeforeEach
    void setUp() {
        user = new User("username", "password", "email@example.com");
        user.setId(1L);
    }

    @Test
    void shouldGetUserById() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        ResponseEntity<User> response = userController.getUserById(1L);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(user.getId(), response.getBody().getId());
        assertEquals(user.getUsername(), response.getBody().getUsername());
        assertEquals(user.getPassword(), response.getBody().getPassword());
        assertEquals(user.getEmail(), response.getBody().getEmail());

        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    void shouldGetAllUsers() {
        List<User> users = Arrays.asList(user);
        when(userRepository.findAll()).thenReturn(users);

        List<User> response = userController.getAllUsers();

        assertNotNull(response);
        assertEquals(1, response.size());
        assertEquals(user.getId(), response.get(0).getId());
        assertEquals(user.getUsername(), response.get(0).getUsername());
        assertEquals(user.getPassword(), response.get(0).getPassword());
        assertEquals(user.getEmail(), response.get(0).getEmail());

        verify(userRepository, times(1)).findAll();
    }

    @Test
    void shouldGetUserByUsername() {
        when(userService.getUserByUsername("username")).thenReturn(user);

        ResponseEntity<User> response = userController.getUserByUsername("username");

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(user.getId(), response.getBody().getId());
        assertEquals(user.getUsername(), response.getBody().getUsername());
        assertEquals(user.getPassword(), response.getBody().getPassword());
        assertEquals(user.getEmail(), response.getBody().getEmail());

        verify(userService, times(1)).getUserByUsername("username");
    }

    @Test
    void shouldAddUser() {
        when(userService.addUser(user)).thenReturn(user);

        BindingResult bindingResult = Mockito.mock(BindingResult.class);
        when(bindingResult.hasErrors()).thenReturn(false);

        ResponseEntity<Void> response = userController.addUser(user, bindingResult);

        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());

        verify(userService, times(1)).addUser(user);
    }


    @Test
    void shouldDeleteUser() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        doNothing().when(userRepository).delete(user);

        ResponseEntity<Void> response = userController.deleteUser(1L);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        verify(userRepository, times(1)).findById(1L);
        verify(userRepository, times(1)).delete(user);
    }
}
