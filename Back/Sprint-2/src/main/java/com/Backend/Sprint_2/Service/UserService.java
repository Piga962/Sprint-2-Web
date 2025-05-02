package com.Backend.Sprint_2.Service;

import com.Backend.Sprint_2.Models.User;
import com.Backend.Sprint_2.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class UserService {


    @Autowired
    private UserRepository userRepository;

    public String createUser(User user) throws ExecutionException, InterruptedException {
        return userRepository.saveUser(user);
    }

    //Modified login
    public User login(String name, String password) throws ExecutionException, InterruptedException {
        return userRepository.login(name, password);
    }

    public User getUserById(String id) throws ExecutionException, InterruptedException {
        return userRepository.getUserById(id);
    }

    public List<User> getAllUsers() throws ExecutionException, InterruptedException {
        return userRepository.getAllUsers();
    }

    public List<User> getAvailableUsers() throws ExecutionException, InterruptedException {
        return userRepository.getAvailableUsers();
    }

    public String updateUser(User user) throws ExecutionException, InterruptedException {
        return userRepository.updateUser(user);
    }

    public String deleteUser(String id) throws ExecutionException, InterruptedException {
        return userRepository.deleteUser(id);
    }

    public String setUserAvailability(String userId, boolean available) throws ExecutionException, InterruptedException {
        User user = userRepository.getUserById(userId);
        if (user != null) {
            user.setAvailable(available);
            return userRepository.updateUser(user);
        }
        return null;
    }
    public List<User> getAllSeniorUsers() throws ExecutionException, InterruptedException {
        return userRepository.getAllSeniorUsers();
    }

}
