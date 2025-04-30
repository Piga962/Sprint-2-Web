package com.Backend.Sprint_2.Repository;

import com.Backend.Sprint_2.Models.Task;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Repository
public class TaskRepository {

    private static final String COLLECTION_NAME = "tasks";

    public String saveTask(Task task) throws ExecutionException, InterruptedException {
        Firestore dbFirestore = FirestoreClient.getFirestore();

        // Si el ID es nulo, Firestore generará uno automáticamente
        if (task.getId() == null || task.getId().isEmpty()) {
            ApiFuture<DocumentReference> addedDocRef = dbFirestore.collection(COLLECTION_NAME).add(task);
            DocumentReference documentReference = addedDocRef.get();
            return documentReference.getId();
        } else {
            // Si ya tiene un ID, lo usamos
            ApiFuture<WriteResult> writeResult = dbFirestore.collection(COLLECTION_NAME).document(task.getId()).set(task);
            writeResult.get();
            return task.getId();
        }
    }

    public Task getTaskById(String id) throws ExecutionException, InterruptedException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        DocumentReference documentReference = dbFirestore.collection(COLLECTION_NAME).document(id);
        ApiFuture<DocumentSnapshot> future = documentReference.get();
        DocumentSnapshot document = future.get();

        if (document.exists()) {
            return document.toObject(Task.class);
        } else {
            return null;
        }
    }

    public List<Task> getAllTasks() throws ExecutionException, InterruptedException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        ApiFuture<QuerySnapshot> future = dbFirestore.collection(COLLECTION_NAME).get();
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();

        List<Task> taskList = new ArrayList<>();
        for (QueryDocumentSnapshot document : documents) {
            taskList.add(document.toObject(Task.class));
        }

        return taskList;
    }

    public List<Task> getTasksByUserId(String userId) throws ExecutionException, InterruptedException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        ApiFuture<QuerySnapshot> future = dbFirestore.collection(COLLECTION_NAME)
                .whereEqualTo("userId", userId)
                .get();

        List<QueryDocumentSnapshot> documents = future.get().getDocuments();

        List<Task> taskList = new ArrayList<>();
        for (QueryDocumentSnapshot document : documents) {
            taskList.add(document.toObject(Task.class));
        }

        return taskList;
    }

    public List<Task> getTasksByStatus(Task.TaskStatus status) throws ExecutionException, InterruptedException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        ApiFuture<QuerySnapshot> future = dbFirestore.collection(COLLECTION_NAME)
                .whereEqualTo("status", status)
                .get();

        List<QueryDocumentSnapshot> documents = future.get().getDocuments();

        List<Task> taskList = new ArrayList<>();
        for (QueryDocumentSnapshot document : documents) {
            taskList.add(document.toObject(Task.class));
        }

        return taskList;
    }

    public List<Task> getTasksByPriority(Task.TaskPriority priority) throws ExecutionException, InterruptedException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        ApiFuture<QuerySnapshot> future = dbFirestore.collection(COLLECTION_NAME)
                .whereEqualTo("priority", priority)
                .get();

        List<QueryDocumentSnapshot> documents = future.get().getDocuments();

        List<Task> taskList = new ArrayList<>();
        for (QueryDocumentSnapshot document : documents) {
            taskList.add(document.toObject(Task.class));
        }

        return taskList;
    }

    public String updateTask(Task task) throws ExecutionException, InterruptedException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        ApiFuture<WriteResult> writeResult = dbFirestore.collection(COLLECTION_NAME).document(task.getId()).set(task);
        writeResult.get();
        return task.getId();
    }

    public String assignTaskToUser(String taskId, String userId) throws ExecutionException, InterruptedException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        DocumentReference taskRef = dbFirestore.collection(COLLECTION_NAME).document(taskId);

        // Primero verificamos que la tarea existe
        DocumentSnapshot taskDoc = taskRef.get().get();
        if (!taskDoc.exists()) {
            return null; // La tarea no existe
        }

        // Actualizamos solo el campo userId
        ApiFuture<WriteResult> future = taskRef.update("userId", userId);
        WriteResult result = future.get();

        return taskId;
    }

    public String deleteTask(String id) throws ExecutionException, InterruptedException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        ApiFuture<WriteResult> writeResult = dbFirestore.collection(COLLECTION_NAME).document(id).delete();
        writeResult.get();
        return "Tarea eliminada con ID: " + id;
    }


}
