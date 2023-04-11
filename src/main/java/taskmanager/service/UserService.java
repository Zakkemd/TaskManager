package taskmanager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import taskmanager.model.User;
import taskmanager.repository.UserRepository;

import java.util.List;

@Service
@Transactional
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public void addUser(User user) {
        userRepository.save(user);
    }

    public User getUserByID(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public void updateUser(User user) {
        userRepository.save(user);
    }

    public void removeUser(Long id) {
        userRepository.deleteUserById(id);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
