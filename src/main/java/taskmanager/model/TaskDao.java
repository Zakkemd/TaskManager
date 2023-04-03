package taskmanager.model;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.List;

@Repository
@Transactional
public class TaskDao {

    @PersistenceContext
    private EntityManager entityManager;

    public void addTask(Task task) {
        entityManager.persist(task);
    }

    public Task getTaskByID(Long id) {
        return entityManager.find(Task.class, id);
    }

    public void updateTask(Task task) {
        entityManager.merge(task);
    }

    public void removeTask(Task task) {
        entityManager.remove(entityManager.contains(task) ? task : entityManager.merge(task));
    }

    public List<Task> getAllTasks() {
        return entityManager.createQuery("SELECT t FROM Task t", Task.class).getResultList();
    }

    public List<Task> getTasksByUser(User user) {
        return entityManager.createQuery("SELECT t FROM Task t WHERE t.user = :user", Task.class)
                .setParameter("user", user)
                .getResultList();
    }

    public List<Task> getTasksByStatus(String status) {
        return entityManager.createQuery("SELECT t FROM Task t WHERE t.status = :status", Task.class)
                .setParameter("status", status)
                .getResultList();
    }

    public List<Task> getTasksByDeadlineRange(LocalDateTime start, LocalDateTime end) {
        return entityManager.createQuery("SELECT t FROM Task t WHERE t.deadline BETWEEN :start AND :end", Task.class)
                .setParameter("start", start)
                .setParameter("end", end)
                .getResultList();
    }

}
