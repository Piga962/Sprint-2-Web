package com.Backend.Sprint_2.Service;

import com.Backend.Sprint_2.Models.Task;
import com.Backend.Sprint_2.Models.User;
import com.Backend.Sprint_2.Repository.TaskRepository;
import com.Backend.Sprint_2.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;

    public String createTask(Task task) throws ExecutionException, InterruptedException {
        return taskRepository.saveTask(task);
    }

    public Task getTaskById(String id) throws ExecutionException, InterruptedException {
        return taskRepository.getTaskById(id);
    }

    public List<Task> getAllTasks() throws ExecutionException, InterruptedException {
        return taskRepository.getAllTasks();
    }

    public List<Task> getTasksByUserId(String userId) throws ExecutionException, InterruptedException {
        return taskRepository.getTasksByUserId(userId);
    }

    public List<Task> getTasksByStatus(Task.TaskStatus status) throws ExecutionException, InterruptedException {
        return taskRepository.getTasksByStatus(status);
    }

    public List<Task> getTasksByPriority(Task.TaskPriority priority) throws ExecutionException, InterruptedException {
        return taskRepository.getTasksByPriority(priority);
    }

    public String updateTask(Task task) throws ExecutionException, InterruptedException {
        return taskRepository.updateTask(task);
    }

    public String assignTaskToUser(String taskId, String userId) throws ExecutionException, InterruptedException {
        // Verificar que el usuario existe y está disponible
        User user = userRepository.getUserById(userId);
        if (user == null || !user.isAvailable()) {
            return null; // Usuario no encontrado o no disponible
        }

        return taskRepository.assignTaskToUser(taskId, userId);
    }

    public String updateTaskStatus(String taskId, Task.TaskStatus status) throws ExecutionException, InterruptedException {
        Task task = taskRepository.getTaskById(taskId);
        if (task != null) {
            task.setStatus(status);
            return taskRepository.updateTask(task);
        }
        return null;
    }

    public String deleteTask(String id) throws ExecutionException, InterruptedException {
        return taskRepository.deleteTask(id);
    }

}
