package com.Backend.Sprint_2.Controllers;


import com.Backend.Sprint_2.Models.Task;
import com.Backend.Sprint_2.Models.User;
import com.Backend.Sprint_2.Service.TaskService;
import com.Backend.Sprint_2.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<?> createTask(@RequestBody Task task) {
        try {
            String taskId = taskService.createTask(task);
            Task newTask = taskService.getTaskById(taskId);
            return new ResponseEntity<>(newTask, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable String id) {
        try {
            Task task = taskService.getTaskById(id);
            if (task != null) {
                return new ResponseEntity<>(task, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<List<Task>> getAllTasks() {
        try {
            List<Task> tasks = taskService.getAllTasks();
            return new ResponseEntity<>(tasks, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Task>> getTasksByUserId(@PathVariable String userId) {
        try {
            List<Task> tasks = taskService.getTasksByUserId(userId);
            return new ResponseEntity<>(tasks, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<Task>> getTasksByStatus(@PathVariable String status) {
        try {
            Task.TaskStatus taskStatus = Task.TaskStatus.valueOf(status.toUpperCase());
            List<Task> tasks = taskService.getTasksByStatus(taskStatus);
            return new ResponseEntity<>(tasks, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/priority/{priority}")
    public ResponseEntity<List<Task>> getTasksByPriority(@PathVariable String priority) {
        try {
            Task.TaskPriority taskPriority = Task.TaskPriority.valueOf(priority.toUpperCase());
            List<Task> tasks = taskService.getTasksByPriority(taskPriority);
            return new ResponseEntity<>(tasks, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateTask(@PathVariable String id, @RequestBody Task task) {
        try {
            task.setId(id);
            String result = taskService.updateTask(task);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping("/{id}/{userId}/assign")
    public ResponseEntity<String> assignTaskToUser(@PathVariable String id, @PathVariable String userId) {
        try {
            String result = taskService.assignTaskToUser(id, userId);
            if (result != null) {
                return new ResponseEntity<>(result, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Usuario no disponible o no encontrado", HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping("/{id}/agentAssign")
    public ResponseEntity<String> agentAssignTask(@PathVariable String id) throws ExecutionException, InterruptedException {

        List<User> users = userService.getAllUsers();
        List<User> seniors = userService.getAllSeniorUsers();
        Task task = taskService.getTaskById(id);

        if (Task.TaskPriority.Low== task.getPriority()) {
            if (tryAssignTask(users, id)) {
                return new ResponseEntity<>(HttpStatus.OK);
            }
        } else if (Task.TaskPriority.Critical== task.getPriority()) {
            if (tryAssignTask(seniors, id)) {
                return new ResponseEntity<>(HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    private boolean tryAssignTask(List<User> candidates, String taskId) throws ExecutionException, InterruptedException {
        for (User user : candidates) {
            if (taskService.getTasksByUserId(user.getId()).isEmpty()) {
                taskService.assignTaskToUser(taskId, user.getId());
                return true;
            }
        }
        for (User user : candidates) {
            if (taskService.getTasksByUserId(user.getId()).size() < 4) {
                taskService.assignTaskToUser(taskId, user.getId());
                return true;
            }
        }
        return false;
    }
}