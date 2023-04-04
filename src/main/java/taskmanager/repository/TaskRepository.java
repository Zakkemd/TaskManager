package taskmanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import taskmanager.model.Task;
import taskmanager.model.User;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findByUser(User user);

    List<Task> findByStatus(String status);

    List<Task> findByDeadlineBetween(LocalDateTime start, LocalDateTime end);

}

