package App.Model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class User {

    private String uid;
    private String displayName;
    private String email;
    private Boolean emailVerified;
    private String photoUrl;
    private ArrayList<Services> services;
    private ArrayList<Widgets> widgets;

    @JsonIgnore
    private static final Firestore db = FirestoreClient.getFirestore();

    public User() {

    }

    public User createNewUser() throws ExecutionException, InterruptedException {
        Map<String, Object> docData = new HashMap<>();
        docData.put("displayName", this.getDisplayName());
        docData.put("email", this.getEmail());
        docData.put("emailVerified", this.getEmailVerified());
        docData.put("photoUrl", this.getPhotoUrl());
        docData.put("services", null);
        docData.put("uid", this.getUid());
        docData.put("widgets", null);

        db.collection("users").document(this.getUid()).set(docData);

        DocumentReference docRef = db.collection("users").document(this.getUid());
        ApiFuture<DocumentSnapshot> future = docRef.get();
        DocumentSnapshot document = future.get();

        User data = document.toObject(User.class);
        return data;

    }

    public User userLogIn() throws ExecutionException, InterruptedException {
        System.out.println("USER LOGIN");
        User response = null;

        // Get user data from Firestore
        DocumentReference docRef = db.collection("users").document(this.getUid());

        // Check Value from database
        ApiFuture<DocumentSnapshot> future = docRef.get();
        DocumentSnapshot document = future.get();

        // check if user exist
        if (document.exists()) {
            response = document.toObject(User.class);
            response.printData();
        } else {
            response = this.createNewUser();
        }
        return response;
    }

    public void printData() {
        System.out.println("User data:");
        System.out.println("    " + this.getUid());
        System.out.println("    " + this.getDisplayName());
        System.out.println("    " + this.getEmail());
        System.out.println("    " + this.getEmailVerified());
        System.out.println("    " + this.getPhotoUrl());
        System.out.println("    " + this.getServices());
        System.out.println("    " + this.getWidgets());
    }

    public String getUid() {
        return uid;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getEmail() {
        return email;
    }

    public Boolean getEmailVerified() {
        return emailVerified;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public ArrayList<Services> getServices() {
        return services;
    }

    public ArrayList<Widgets> getWidgets() {
        return widgets;
    }
}
