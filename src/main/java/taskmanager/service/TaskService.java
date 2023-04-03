package taskmanager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import taskmanager.model.Task;
import taskmanager.model.TaskDao;
import taskmanager.model.User;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TaskService {

    private final TaskDao taskDao;

    @Autowired
    public TaskService(TaskDao taskDao) {
        this.taskDao = taskDao;
    }

    public void addTask(Task task) {
        taskDao.addTask(task);
    }

    public Task getTaskById(Long id) {
        return taskDao.getTaskByID(id);
    }

    public void updateTask(Task task) {
        taskDao.updateTask(task);
    }

    public void removeTask(Task task) {
        taskDao.removeTask(task);
    }

    public List<Task> getAllTasks() {
        return taskDao.getAllTasks();
    }

    public List<Task> getTasksByUser(User user) {
        return taskDao.getTasksByUser(user);
    }

    public List<Task> getTasksByStatus(String status) {
        return taskDao.getTasksByStatus(status);
    }

    public List<Task> getTasksByDeadlineRange(LocalDateTime start, LocalDateTime end) {
        return taskDao.getTasksByDeadlineRange(start, end);
    }

}
