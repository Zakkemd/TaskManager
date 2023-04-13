package taskmanager.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import taskmanager.model.Task;
import taskmanager.model.User;
import taskmanager.service.TaskService;
import taskmanager.service.UserService;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(MockitoJUnitRunner.class)
public class TaskControllerTest {

    private MockMvc mockMvc;

    @Mock
    private TaskService taskService;

    @Mock
    private UserService userService;

    @InjectMocks
    private TaskController taskController;
    private ObjectMapper objectMapper;

    @Before
    public void setUp() {
        objectMapper = new ObjectMapper();
        mockMvc = MockMvcBuilders.standaloneSetup(taskController).build();
        objectMapper.registerModule(new JavaTimeModule());
    }


    @Test
    public void shouldGetTaskById() throws Exception {
        Task task = new Task("Task1", "Task1 description", LocalDateTime.now(), "New")
                .withId(1L);

        when(taskService.getTaskByID(1L)).thenReturn(task);

        mockMvc.perform(get("/api/tasks/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(1L))
                .andExpect(jsonPath("name").value("Task1"));
    }



    // Other test methods go here

    @Test
    public void shouldGetAllTasks() throws Exception {
        Task task1 = new Task("Task1", "Task1 description", LocalDateTime.now(), "New")
                .withId(1L);
        Task task2 = new Task("Task2", "Task2 description", LocalDateTime.now(), "In progress")
                .withId(2L);
        List<Task> tasks = Arrays.asList(task1, task2);

        when(taskService.getAllTasks()).thenReturn(tasks);

        mockMvc.perform(get("/api/tasks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].name").value("Task1"))
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].name").value("Task2"));
    }

    @Test
    public void shouldGetTasksByUser() throws Exception {
        User user = new User("John",  "john@example.com", "password")
                .withId(1L);
        Task task1 = new Task("Task1", "Task1 description", LocalDateTime.now(), "New")
                .withId(1L)
                .withUser(user);
        Task task2 = new Task("Task2", "Task2 description", LocalDateTime.now(), "In progress")
                .withId(2L)
                .withUser(user);
        List<Task> tasks = Arrays.asList(task1, task2);

        when(userService.getUserByID(1L)).thenReturn(user);
        when(taskService.getTasksByUser(user)).thenReturn(tasks);

        mockMvc.perform(get("/api/tasks/user/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].name").value("Task1"))
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].name").value("Task2"));
    }

    @Test
    public void shouldGetTasksByStatus() throws Exception {
        Task task1 = new Task("Task1", "Task1 description", LocalDateTime.now(), "New")
                .withId(1L);
        Task task2 = new Task("Task2", "Task2 description", LocalDateTime.now(), "New")
                .withId(2L);
        List<Task> tasks = Arrays.asList(task1, task2);

        when(taskService.getTasksByStatus("New")).thenReturn(tasks);

        mockMvc.perform(get("/api/tasks/status/New"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].name").value("Task1"))
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].name").value("Task2"));
    }

    @Test
    public void shouldGetTasksByDeadlineRange() throws Exception {
        LocalDateTime start = LocalDateTime.parse("2023-01-01T00:00:00");
        LocalDateTime end = LocalDateTime.parse("2023-12-31T23:59:59");
        Task task1 = new Task("Task1", "Task1 description", LocalDateTime.parse("2023-02-01T10:00:00"), "New")
                .withId(1L);
        Task task2 = new Task("Task2", "Task2 description", LocalDateTime.parse("2023-03-01T10:00:00"), "In progress")
                .withId(2L);
        List<Task> tasks = Arrays.asList(task1, task2);

        when(taskService.getTasksByDeadlineRange(start, end)).thenReturn(tasks);

        mockMvc.perform(get("/api/tasks/deadline?start=2023-01-01T00:00:00&end=2023-12-31T23:59:59"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].name").value("Task1"))
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].name").value("Task2"));
    }





    @Test
    public void shouldUpdateTask() throws Exception {
        Task existingTask = new Task("Task1", "Task1 description", LocalDateTime.parse("2023-02-01T10:00:00"), "New")
                .withId(1L);
        Task updatedTask = new Task("Updated Task1", "Updated Task1 description", LocalDateTime.parse("2023-02-01T10:00:00"), "In progress")
                .withId(1L);

        when(taskService.getTaskByID(1L)).thenReturn(existingTask);

        mockMvc.perform(put("/api/tasks/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedTask)))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldDeleteTask() throws Exception {
        Task task = new Task("Task1", "Task1 description", LocalDateTime.parse("2023-02-01T10:00:00"), "New")
                .withId(1L);

        when(taskService.getTaskByID(1L)).thenReturn(task);

        mockMvc.perform(delete("/api/tasks/1"))
                .andExpect(status().isOk());
    }


}
