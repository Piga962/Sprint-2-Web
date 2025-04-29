package com.Backend.Sprint_2.Models;

import com.google.cloud.firestore.annotation.DocumentId;

public class Task {
    @DocumentId
    private String id;
    private String description;
    private TaskStatus status;
    private TaskPriority priority;
    private String userId; // ID del usuario asignado

    // Enumeraciones para estado y prioridad
    public enum TaskStatus {
        Complete, Incomplete
    }

    public enum TaskPriority {
        Low, Critical
    }

    // Constructor por defecto (necesario para Firestore)
    public Task() {}

    // Constructor completo
    public Task(String id, String description, TaskStatus status, TaskPriority priority, String userId) {
        this.id = id;
        this.description = description;
        this.status = status;
        this.priority = priority;
        this.userId = userId;
    }

    // Getters y Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public TaskPriority getPriority() {
        return priority;
    }

    public void setPriority(TaskPriority priority) {
        this.priority = priority;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}