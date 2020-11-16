package App.Model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import com.google.gson.Gson;
import com.google.protobuf.Any;

import javax.swing.text.Document;
import java.net.URL;
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

    public void createNewUser() throws ExecutionException, InterruptedException {
        System.out.println("CREATE NEW USER");
        Map<String, Object> docData = new HashMap<>();
        docData.put("displayName", this.getDisplayName());
        docData.put("email", this.getEmail());
        docData.put("emailVerified", this.getEmailVerified());
        docData.put("photoUrl", this.getPhotoUrl());
        docData.put("services", null);
        docData.put("uid", this.getUid());
        docData.put("widgets", null);

        db.collection("users").document(this.getUid()).set(docData);
    }

    public User userLogIn() throws ExecutionException, InterruptedException {
        System.out.println("USER LOGIN");
        User response = null;
        this.printData();

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
            this.createNewUser();
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
