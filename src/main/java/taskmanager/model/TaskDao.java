package taskmanager.model;


import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.time.LocalDateTime;
import java.util.List;

@Stateless
public class TaskDao {

    @PersistenceContext
    private EntityManager entityManager;


    public TaskDao(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

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
        entityManager.remove(entityManager.merge(task));
    }

    public List<Task> getAllTasks() {
        TypedQuery<Task> query = entityManager.createQuery("SELECT t FROM Task t", Task.class);
        return query.getResultList();
    }

    public List<Task> getTasksByUser(User user) {
        TypedQuery<Task> query = entityManager.createQuery("SELECT t FROM Task t WHERE t.user = :user", Task.class);
        query.setParameter("user", user);
        return query.getResultList();
    }

    public List<Task> getTasksByStatus(String status) {
        TypedQuery<Task> query = entityManager.createQuery("SELECT t FROM Task t WHERE t.status = :status", Task.class);
        query.setParameter("status", status);
        return query.getResultList();
    }

    public List<Task> getTasksByDeadlineRange(LocalDateTime start, LocalDateTime end) {
        TypedQuery<Task> query = entityManager.createQuery("SELECT t FROM Task t WHERE t.deadline BETWEEN :start AND :end", Task.class);
        query.setParameter("start", start);
        query.setParameter("end", end);
        return query.getResultList();


}


}
