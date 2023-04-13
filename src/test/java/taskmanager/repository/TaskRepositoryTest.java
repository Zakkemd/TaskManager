package taskmanager.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import taskmanager.model.Task;
import taskmanager.model.User;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskRepositoryTest {

    @Mock
    private TaskRepository taskRepository;

    private User user;
    private Task task1;
    private Task task2;

    @BeforeEach
    void setUp() {
        user = new User("username", "password", "email@example.com");
        user.setId(1L);

        task1 = new Task("Task 1", "Task 1 description", LocalDateTime.now(), "IN_PROGRESS")
                .withId(1L)
                .withUser(user);

        task2 = new Task("Task 2", "Task 2 description", LocalDateTime.now().plusDays(1), "COMPLETED")
                .withId(2L)
                .withUser(user);
    }

    @Test
    void findByUser() {
        List<Task> tasks = Arrays.asList(task1, task2);

        when(taskRepository.findByUser(user)).thenReturn(tasks);

        List<Task> result = taskRepository.findByUser(user);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(task1, result.get(0));
        assertEquals(task2, result.get(1));

        verify(taskRepository, times(1)).findByUser(user);
    }

    @Test
    void findByStatus() {
        List<Task> tasks = Arrays.asList(task1);

        when(taskRepository.findByStatus("IN_PROGRESS")).thenReturn(tasks);

        List<Task> result = taskRepository.findByStatus("IN_PROGRESS");

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(task1, result.get(0));

        verify(taskRepository, times(1)).findByStatus("IN_PROGRESS");
    }

    @Test
    void findByDeadlineBetween() {
        LocalDateTime start = LocalDateTime.now().minusDays(1);
        LocalDateTime end = LocalDateTime.now().plusDays(2);
        List<Task> tasks = Arrays.asList(task1, task2);

        when(taskRepository.findByDeadlineBetween(start, end)).thenReturn(tasks);

        List<Task> result = taskRepository.findByDeadlineBetween(start, end);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(task1, result.get(0));
        assertEquals(task2, result.get(1));

        verify(taskRepository, times(1)).findByDeadlineBetween(start, end);
    }
}
