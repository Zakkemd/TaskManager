package taskmanager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import taskmanager.model.User;
import taskmanager.model.UserDao;

import java.util.List;

@Service
@Transactional
public class UserService {

    private final UserDao userDao;

    @Autowired
    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public void addUser(User user) {
        userDao.addUser(user);
    }

    public User getUserByID(Long id) {
        return userDao.getUserByID(id);
    }

    public User getUserByUsername(String username) {
        return userDao.getUserByUsername(username);
    }

    public void removeUser(User user) {
        userDao.removeUser(user);
    }

    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }
}
