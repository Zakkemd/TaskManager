package testmanager.test;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import taskmanager.MainApplication;
import taskmanager.model.Task;
import taskmanager.model.User;
import taskmanager.service.TaskService;
import taskmanager.service.UserService;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {MainApplication.class})
public class TaskServiceTest {

    @Autowired
    private TaskService taskService;

    @Autowired
    private UserService userService;

    @Test
    public void testAddTask() {
        User user = new User();
        user.setUsername("testuser");
        user.setEmail("testuser@example.com");
        user.setPassword("password");

        userService.addUser(user);

        Task task = new Task();
        task.setName("Example Task");
        task.setDescription("This is an example task.");
        task.setStatus("New");
        task.setUser(user);

        taskService.addTask(task);

        Task savedTask = taskService.getTaskByID(task.getId());
        assertEquals(task, savedTask);
    }
}
