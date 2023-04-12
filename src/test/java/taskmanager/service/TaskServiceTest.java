package taskmanager.service;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.mockito.Mockito;
import taskmanager.model.Task;
import taskmanager.repository.TaskRepository;

import java.time.LocalDateTime;
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

}