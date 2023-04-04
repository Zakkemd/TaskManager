package testmanager.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import taskmanager.config.ApplicationConfig;
import taskmanager.model.Task;
import taskmanager.service.TaskService;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {ApplicationConfig.class})
public class TaskServiceTest {

    @Autowired
    private TaskService taskService;

    @Test
    public void testAddTask() {
        Task task = new Task();
        task.setName("Example Task");
        task.setDescription("This is an example task.");
        task.setStatus("New");

        taskService.addTask(task);

        Task savedTask = taskService.getTaskById(task.getId());
        assertEquals(task, savedTask);
    }
}
