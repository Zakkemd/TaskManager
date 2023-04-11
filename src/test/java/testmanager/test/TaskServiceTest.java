package testmanager.test;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import taskmanager.app.MainApplication;
import taskmanager.model.Task;
import taskmanager.model.User;
import taskmanager.repository.TaskRepository;
import taskmanager.repository.UserRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {MainApplication.class})
public class TaskServiceTest {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testAddTask() {
        User user = new User();
        user.setUsername("testuser");
        user.setEmail("testuser@example.com");
        user.setPassword("password");

        userRepository.save(user);

        Task task = new Task();
        task.setName("Example Task");
        task.setDescription("This is an example task.");
        task.setStatus("New");
        task.setUser(user);

        taskRepository.save(task);

        Task savedTask = taskRepository.findById(task.getId()).orElse(null);
        assertEquals(task, savedTask);
    }
}

