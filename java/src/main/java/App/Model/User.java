package App.Model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.api.client.auth.oauth2.RefreshTokenRequest;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import twitter4j.auth.RequestToken;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
        User response = null;

        // Get user data from Firestore
        DocumentReference docRef = db.collection("users").document(this.getUid());

        // Check Value from database
        ApiFuture<DocumentSnapshot> future = docRef.get();
        DocumentSnapshot document = future.get();

        // check if user exist
        if (document.exists()) {
            response = document.toObject(User.class);
        } else {
            response = this.createNewUser();
        }
        return response;
    }

    public void addRefreshToken(String uid, String serviceName, String RequestToken) throws ExecutionException, InterruptedException {
        DocumentReference docRef = db.collection("users").document(this.getUid());

        ApiFuture<DocumentSnapshot> future = docRef.get();
        DocumentSnapshot document = future.get();

        if (document.exists()) {
            User response = document.toObject(User.class);
            ArrayList<Services> services = response.getServices();
            Boolean done = false;
            for (Services service: services) {
                if (service.getName() == serviceName) {
                    service.setRequestToken(RequestToken);
                    done = true;
                }
            }
            if (!done) {

            }

        }
    }

    public User addWidget(String uid, String serviceName, Widgets new_widget) throws ExecutionException, InterruptedException {
        //new_widget.printData();
//        if (new_widget.getUid() == null) {
//            new_widget.generateUid();
//        }
        DocumentReference docRef = db.collection("users").document(uid);

        ApiFuture<DocumentSnapshot> future = docRef.get();
        DocumentSnapshot document = future.get();

        if (document.exists()) {
            User user = document.toObject(User.class);
            ArrayList<Services> services = user.getServices();
            for (Services service: services) {
                if (service.getName().equals(serviceName)) {
                    ArrayList<Widgets> widgets = service.getWidgets();
                    if (widgets == null || widgets.size() == 0) {
                        widgets = new ArrayList<>();
                        widgets.add(new_widget);
                    } else {
                        boolean done = false;
                        for (Widgets widget : widgets) {
                            if (widget.getPosition().get("id").equals(new_widget.getPosition().get("id"))) {
                                widget.setParams(new_widget.getParams());
                                widget.setPosition(new_widget.getPosition());
                                done = true;
                            }
                        }
                        if (!done) {
                            widgets.add(new_widget);
                        }
                    }
                    service.setWidgets(widgets);
                 }
            }
            docRef.update("services", services);
            return user;
        }
        return null;
    }

    public User removeWidget(String uid, Widgets widget) throws ExecutionException, InterruptedException {
        DocumentReference docRef = db.collection("users").document(uid);
        ApiFuture<DocumentSnapshot> future = docRef.get();
        DocumentSnapshot document = future.get();

        if (document.exists()) {
            User user = document.toObject(User.class);
            ArrayList<Services> services = user.getServices();
            for (Services service: services) {
                ArrayList<Widgets> widgets = service.getWidgets();
                if (widgets == null) {
                    return user;
                } else {
                    widgets.removeIf(elem -> widget.getPosition().get("id").equals(elem.getPosition().get("id")));
                }
                service.setWidgets(widgets);
            }
            docRef.update("services", services);
            return user;
        }
        return null;
    }

//    public User updateUserWidgets(String uid, Widgets widget) throws ExecutionException, InterruptedException {
//        System.out.println("UID = " + uid);
//        widget.printData();
//        DocumentReference docRef = db.collection("users").document(uid);
//
//        ApiFuture<DocumentSnapshot> future = docRef.get();
//        DocumentSnapshot document = future.get();
//
//        if (document.exists()) {
//            User old_infos = document.toObject(User.class);
//            docRef.update("widgets", this.getWidgets());
//            System.out.println("OLD INFOS = ");
//            old_infos.printData();
//            System.out.println("NEW INFOS = ");
//            this.printData();
//        } else {
//            System.out.println("User didn't exist !");
//        }
//        return null;
//    }

    public void setServices(ArrayList<Services> services) {
        this.services = services;
    }

    public void setWidgets(ArrayList<Widgets> widgets) {
        this.widgets = widgets;
    }

    public void createService(String uid, String RequestToken, String RequestTokenSecret, String serviceName) throws ExecutionException, InterruptedException {
        Services new_service = new Services();
        new_service.setName(serviceName);
        new_service.setWidgets(null);
        new_service.setRequestToken(RequestToken);
        new_service.setRequestTokenSecret(RequestTokenSecret);
        new_service.setAccessToken(null);

        DocumentReference docRef = db.collection("users").document(uid);
        ApiFuture<DocumentSnapshot> future = docRef.get();
        DocumentSnapshot document = future.get();

        if (document.exists()) {
            User user = document.toObject(User.class);
            ArrayList<Services> services = user.getServices();
            if (services == null) {
                services = new ArrayList<Services>();
                services.add(new_service);
            } else {
                boolean find = false;
                for (Services service: services) {
                    if (service.getName().equals(serviceName)) {
                        service.setRequestToken(RequestToken);
                        service.setRequestTokenSecret(RequestTokenSecret);
                        find = true;
                    }
                }
                if (!find) {
                    services.add(new_service);
                }
            }
            docRef.update("services", services);
        }
    }

    public void updateServices(Services service) {
        System.out.println(service);
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
        System.out.println();
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
