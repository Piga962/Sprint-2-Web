package com.Backend.Sprint_2.Repository;

import com.Backend.Sprint_2.Models.User;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Repository
public class UserRepository {

    private static final String COLLECTION_NAME = "users";

    // Create User
    public String saveUser(User user) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();

        if(user.getId() == null || user.getId().isEmpty()){
            ApiFuture<DocumentReference> addedDocRef = db.collection(COLLECTION_NAME).add(user);
            DocumentReference documentReference = addedDocRef.get();
            return documentReference.getId();
        } else{
            ApiFuture<WriteResult> writeResult = db.collection(COLLECTION_NAME).document(user.getId()).set(user);
            writeResult.get();
            return user.getId();
        }
    }

    //Login User modified
    public User login(String name, String password) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        ApiFuture<QuerySnapshot> future = db.collection(COLLECTION_NAME)
            .whereEqualTo("name", name)
            .whereEqualTo("password", password)
            .whereEqualTo("available", true)
            .get();
    
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();
        if (!documents.isEmpty()) {
            return documents.get(0).toObject(User.class);
        } else {
            return null;
        }
    }
    

    // Get User by Id
    public User getUserById(String id) throws ExecutionException, InterruptedException{
        Firestore db = FirestoreClient.getFirestore();
        DocumentReference documentReference = db.collection(COLLECTION_NAME).document(id);
        ApiFuture<DocumentSnapshot> future = documentReference.get();
        DocumentSnapshot document = future.get();

        if (document.exists()){
            return document.toObject(User.class);
        } else{
            return null;
        }
    }

    public List<User> getAllUsers() throws ExecutionException, InterruptedException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        ApiFuture<QuerySnapshot> future = dbFirestore.collection(COLLECTION_NAME).get();
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();

        List<User> userList = new ArrayList<>();
        for (QueryDocumentSnapshot document : documents) {
            userList.add(document.toObject(User.class));
        }

        return userList;
    }

    public List<User> getAllSeniorUsers() throws ExecutionException, InterruptedException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        ApiFuture<QuerySnapshot> future = dbFirestore.collection(COLLECTION_NAME).get();
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();

        List<User> userList = new ArrayList<>();
        for (QueryDocumentSnapshot document : documents) {
            //if document.userlevel == "Senior"
            String level = document.getString("level");
            if("Senior".equalsIgnoreCase(level)){
                userList.add(document.toObject(User.class));
            }
        }
        return userList;
    }

    public List<User> getAvailableUsers() throws ExecutionException, InterruptedException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        ApiFuture<QuerySnapshot> future = dbFirestore.collection(COLLECTION_NAME)
                .whereEqualTo("available", true)
                .get();

        List<QueryDocumentSnapshot> documents = future.get().getDocuments();

        List<User> userList = new ArrayList<>();
        for (QueryDocumentSnapshot document : documents) {
            userList.add(document.toObject(User.class));
        }

        return userList;
    }

    public String updateUser(User user) throws ExecutionException, InterruptedException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        ApiFuture<WriteResult> writeResult = dbFirestore.collection(COLLECTION_NAME).document(user.getId()).set(user);
        writeResult.get();
        return user.getId();
    }

    public String deleteUser(String id) throws ExecutionException, InterruptedException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        ApiFuture<WriteResult> writeResult = dbFirestore.collection(COLLECTION_NAME).document(id).delete();
        writeResult.get();
        return "Usuario eliminado con ID: " + id;
    }

}
