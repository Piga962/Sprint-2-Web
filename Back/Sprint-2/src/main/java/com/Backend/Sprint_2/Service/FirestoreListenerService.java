package com.Backend.Sprint_2.Service;

import com.Backend.Sprint_2.Models.Task;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.ListenerRegistration;
import com.google.firebase.FirebaseApp;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.ArrayList;
import java.util.List;

@Service
public class FirestoreListenerService {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private FirebaseApp firebaseApp;

    private final List<ListenerRegistration> listeners = new ArrayList<>();

    @PostConstruct
    public void initializeFirestoreListeners() {
        setupTaskListener();
        setupUserListener();
        System.out.println("Firestore listeners initialized.");
    }

    private void setupTaskListener() {
        Firestore firestore = FirestoreClient.getFirestore();

        ListenerRegistration registration = firestore.collection("tasks")
                .addSnapshotListener((snapshot, e) -> {
                    if (e != null){
                        System.out.println("Error listening to tasks: " + e);
                        return;
                    }

                    List<Task> tasks = new ArrayList<>();
                    for (DocumentSnapshot doc : snapshot.getDocuments()){
                        tasks.add(doc.toObject(Task.class));
                    }

                    messagingTemplate.convertAndSend("/topic/tasks", tasks);

                });

        listeners.add(registration);

    }

    private void setupUserListener() {
        Firestore firestore = FirestoreClient.getFirestore();

        ListenerRegistration registration = firestore.collection("users")
                .addSnapshotListener((snapshot, e) -> {
                    if (e != null){
                        System.out.println("Error listening to users: " + e);
                        return;
                    }

                    List<String> users = new ArrayList<>();
                    for (DocumentSnapshot doc : snapshot.getDocuments()){
                        users.add(doc.getId());
                    }

                    messagingTemplate.convertAndSend("/topic/users", users);

                });

        listeners.add(registration);

    }

    @PreDestroy
    public void cleanupListeners(){
        for (ListenerRegistration registration : listeners){
            registration.remove();
        }
        listeners.clear();
        System.out.println("Firestore listeners cleaned up.");
    }

}
