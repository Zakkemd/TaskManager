package taskmanager.service;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import taskmanager.model.Task;
import taskmanager.model.User;
import taskmanager.repository.TaskRepository;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class TaskServiceTest {

    private final TaskRepository taskRepository = Mockito.mock(TaskRepository.class);
    private final TaskService taskService = new TaskService(taskRepository);

    @Test
    public void shouldReturnTaskCorrectly() {
        //given
        Task task = getTask();
        Mockito.when(taskRepository.findById(1L)).thenReturn(Optional.of(task));

        //when
        Task result = taskService.getTaskByID(1L);

        //then
        Assertions.assertThat(result).isEqualTo(task);
    }

    private Task getTask() {
        return new Task()
                .withId(1L)
                .withName("taskName")
                .withDescription("taskDescription")
                .withDeadline(LocalDateTime.of(2023, 12, 1, 12, 30))
                .withStatus("NOT_COMPLETED");
    }


    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void shouldAddTask() {
        // given
        Task task = getTask();
        Mockito.when(taskRepository.save(task)).thenReturn(task);

        // when
        taskService.addTask(task);

        // then
        Mockito.verify(taskRepository, Mockito.times(1)).save(task);
    }

    @Test
    public void shouldUpdateTask() {
        // given
        Task task = getTask();
        Mockito.when(taskRepository.save(task)).thenReturn(task);

        // when
        taskService.updateTask(task);

        // then
        Mockito.verify(taskRepository, Mockito.times(1)).save(task);
    }

    @Test
    public void shouldRemoveTask() {
        // given
        Long taskId = 1L;

        // when
        taskService.removeTask(taskId);

        // then
        Mockito.verify(taskRepository, Mockito.times(1)).deleteById(taskId);
    }

    @Test
    public void shouldGetAllTasks() {
        // given
        Task task = getTask();
        Mockito.when(taskRepository.findAll()).thenReturn(Collections.singletonList(task));

        // when
        List<Task> tasks = taskService.getAllTasks();

        // then
        Assertions.assertThat(tasks).containsExactly(task);
    }

    @Test
    public void shouldGetTasksByUser() {
        // given
        Task task = getTask();
        User user = new User("username", "password", "email@test.com");
        Mockito.when(taskRepository.findByUser(user)).thenReturn(Collections.singletonList(task));

        // when
        List<Task> tasks = taskService.getTasksByUser(user);

        // then
        Assertions.assertThat(tasks).containsExactly(task);
    }

    @Test
    public void shouldGetTasksByStatus() {
        // given
        Task task = getTask();
        String status = "NOT_COMPLETED";
        Mockito.when(taskRepository.findByStatus(status)).thenReturn(Collections.singletonList(task));

        // when
        List<Task> tasks = taskService.getTasksByStatus(status);

        // then
        Assertions.assertThat(tasks).containsExactly(task);
    }

    @Test
    public void shouldGetTasksByDeadlineRange() {
        // given
        Task task = getTask();
        LocalDateTime start = LocalDateTime.of(2023, 11, 1, 0, 0);
        LocalDateTime end = LocalDateTime.of(2023, 12, 31, 23, 59);
        Mockito.when(taskRepository.findByDeadlineBetween(start, end)).thenReturn(Collections.singletonList(task));

        // when
        List<Task> tasks = taskService.getTasksByDeadlineRange(start, end);

        // then
        Assertions.assertThat(tasks).containsExactly(task);
    }


}
