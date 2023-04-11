package taskmanager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import taskmanager.model.Task;
import taskmanager.model.User;
import taskmanager.repository.TaskRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    public void addTask(Task task) {
        taskRepository.save(task);
    }

    public Task getTaskByID(Long id) {
        return taskRepository.findById(id).orElse(null);
    }

    public void updateTask(Task task) {
        taskRepository.save(task);
    }

    public void removeTask(Long id) {
        taskRepository.deleteById(id);
    }

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public List<Task> getTasksByUser(User user) {
        return taskRepository.findByUser(user);
    }

    public List<Task> getTasksByStatus(String status) {
        return taskRepository.findByStatus(status);
    }

    public List<Task> getTasksByDeadlineRange(LocalDateTime start, LocalDateTime end) {
        return taskRepository.findByDeadlineBetween(start, end);
    }
}
