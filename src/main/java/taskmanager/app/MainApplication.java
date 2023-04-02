package taskmanager.app;

import taskmanager.model.Task;
import taskmanager.model.TaskDao;
import taskmanager.model.User;
import taskmanager.model.UserDao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.time.LocalDate;

public class MainApplication {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("taskmanager");
        EntityManager em = emf.createEntityManager();

        UserDao userDao = new UserDao(em);
        TaskDao taskDao = new TaskDao(em);

        User user = new User("John", "Doe", "johndoe@example.com");
        Task task = new Task("Zadanie 1", "Opis zadania 1", LocalDate.now().plusDays(7).atStartOfDay(), "NEW");


        em.getTransaction().begin();

        userDao.addUser(user);
        taskDao.addTask(task);

        em.getTransaction().commit();

        em.close();
        emf.close();
    }
}
