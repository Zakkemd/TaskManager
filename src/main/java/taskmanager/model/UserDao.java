package taskmanager.model;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Stateless
public class UserDao {

    @PersistenceContext
    private EntityManager entityManager;


    public UserDao(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void addUser(User user) {
        entityManager.persist(user);
    }

    public User getUserByID(Long id) {
        return entityManager.find(User.class, id);
    }

    public User getUserByUsername(String username) {
        TypedQuery<User> query = entityManager.createQuery("SELECT u FROM User u WHERE u.username = :username", User.class);
        query.setParameter("username", username);
        List<User> users = query.getResultList();

        if (users.isEmpty()) {
            return null;
        } else {
            return users.get(0);
        }

    }

    public void removeUser(User user) {
        entityManager.remove(entityManager.merge(user));
    }

    public List<User> getAllUsers() {
        TypedQuery<User> query = entityManager.createQuery("SELECT u FROM User u", User.class);
        return query.getResultList();
    }
}
