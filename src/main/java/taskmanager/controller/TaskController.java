package taskmanager.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import taskmanager.model.Task;
import taskmanager.model.TaskStatus;
import taskmanager.model.User;
import taskmanager.service.TaskService;
import taskmanager.service.UserService;

import javax.validation.Valid;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;
    private final UserService userService;

    private static final Logger LOGGER = LoggerFactory.getLogger(TaskController.class);

    @Autowired
    public TaskController(TaskService taskService, UserService userService) {
        this.taskService = taskService;
        this.userService = userService;
    }

    @GetMapping("/{taskId}")
    public ResponseEntity<Task> getTaskById(@PathVariable Long taskId) {
        Task task = taskService.getTaskByID(taskId);
        if (task != null) {
            return new ResponseEntity<>(task, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public ResponseEntity<List<Task>> getAllTasks() {
        return new ResponseEntity<>(taskService.getAllTasks(), HttpStatus.OK);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Task>> getTasksByUser(@PathVariable Long userId) {
        User user = userService.getUserByID(userId);
        if (user != null) {
            return new ResponseEntity<>(taskService.getTasksByUser(user), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<Task>> getTasksByStatus(@PathVariable String status) {
        TaskStatus taskStatus;
        try {
            taskStatus = TaskStatus.valueOf(status.toUpperCase());
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(taskService.getTasksByStatus(taskStatus), HttpStatus.OK);
    }



    @GetMapping("/deadline")
    public ResponseEntity<List<Task>> getTasksByDeadlineRange(@RequestParam("start") String start, @RequestParam("end") String end) {
        LocalDateTime startDate = LocalDateTime.parse(start);
        LocalDateTime endDate = LocalDateTime.parse(end);
        return new ResponseEntity<>(taskService.getTasksByDeadlineRange(startDate, endDate), HttpStatus.OK);
    }

    @PostMapping("/api/tasks")
    public ResponseEntity<?> addTask(@Valid @RequestBody Task task, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ValidationException();
        }
        Task createdTask = taskService.addTask(task);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create("/api/tasks/" + createdTask.getId()));
        return new ResponseEntity<>(createdTask, headers, HttpStatus.CREATED);
    }



    @PutMapping("/{taskId}")
    public ResponseEntity<Void> updateTask(@PathVariable Long taskId, @RequestBody Task task) {
        Task existingTask = taskService.getTaskByID(taskId);
        if (existingTask != null) {
            Task updatedTask = new Task()
                    .withId(existingTask.getId())
                    .withName(task.getName())
                    .withDescription(task.getDescription())
                    .withDeadline(task.getDeadline())
                    .withStatus(task.getStatus())
                    .withUser(existingTask.getUser());
            taskService.updateTask(updatedTask);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long taskId) {
        Task task = taskService.getTaskByID(taskId);
        if (task != null) {
            taskService.removeTask(taskId);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<Void> handleValidationException() {
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    public static class ValidationException extends RuntimeException {
    }
}
