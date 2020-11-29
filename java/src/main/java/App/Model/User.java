package App.Model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * Class User
 */

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

    /**
     * Public User
     */
    public User() {
        this.uid = null;
        this.displayName = null;
        this.email = null;
        this.emailVerified = null;
        this.photoUrl = null;
        this.services = null;
        this.widgets = null;
    }

    /**
     * Create New User
     * @return
     * a new User created in the DB
     * @throws ExecutionException
     * if the connection is interrupted
     * @throws InterruptedException
     * if the connection is interrupted
     */
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

        return document.toObject(User.class);

    }

    /**
     * User Login
     * @return
     * the login in user
     * @throws ExecutionException
     * if the connection is interrupted
     * @throws InterruptedException
     * if the connection is interrupted
     */
    public User userLogIn() throws ExecutionException, InterruptedException {
        User response = null;

        DocumentReference docRef = db.collection("users").document(this.getUid());
        ApiFuture<DocumentSnapshot> future = docRef.get();
        DocumentSnapshot document = future.get();
        if (document.exists()) {
            response = document.toObject(User.class);
        } else {
            response = this.createNewUser();
        }
        return response;
    }

    /**
     * Update Widget
     * @param uid
     * uid of the user
     * @param new_widget
     * the new widgets to setup
     * @return
     * User with this modified data
     * @throws ExecutionException
     * if the connection is interrupted
     * @throws InterruptedException
     * if the connection is interrupted
     */
    public User updateWidget(String uid, Widgets new_widget) throws ExecutionException, InterruptedException {
        DocumentReference docRef = db.collection("users").document(uid);
        ApiFuture<DocumentSnapshot> future = docRef.get();
        DocumentSnapshot document = future.get();

        if (document.exists()) {
            User user = document.toObject(User.class);
            assert user != null;
            ArrayList<Services> services = user.getServices();
            for (Services service: services) {
                ArrayList<Widgets> widgets = service.getWidgets();
                if (widgets == null) {
                    continue;
                }
                for (Widgets widget: widgets) {
                    if (widget.getPosition().get("id").equals(new_widget.getPosition().get("id"))) {
                        widget.setPosition(new_widget.getPosition());
                        widget.setParams(new_widget.getParams());
                    }
                }
            }
            docRef.update("services", services);
            return user;
        }
        return null;
    }

    /**
     * Add Widget
     * @param uid
     * uid of the user
     * @param serviceName
     * service to create widgets
     * @param new_widget
     * new widgets to create
     * @return
     * user with new data
     * @throws ExecutionException
     * if the connection is interrupted
     * @throws InterruptedException
     * if the connection is interrupted
     */
    public User addWidget(String uid, String serviceName, Widgets new_widget) throws ExecutionException, InterruptedException {
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

    /**
     * Remove Widget
     * @param uid
     * uid of the user
     * @param widget
     * widget to remove
     * @return
     * new user
     * @throws ExecutionException
     * if the connection is interrupted
     * @throws InterruptedException
     * if the connection is interrupted
     */
    public User removeWidget(String uid, Widgets widget) throws ExecutionException, InterruptedException {
        DocumentReference docRef = db.collection("users").document(uid);
        ApiFuture<DocumentSnapshot> future = docRef.get();
        DocumentSnapshot document = future.get();

        if (document.exists()) {
            User user = document.toObject(User.class);
            assert user != null;
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

    /**
     * Get Widget List
     * @param uid
     * uid of the user
     * @return
     * list of services
     * @throws ExecutionException
     * if the connection is interrupted
     * @throws InterruptedException
     * if the connection is interrupted
     */
    public ArrayList<Services> getWidgetsList(String uid) throws ExecutionException, InterruptedException {
        DocumentReference docRef = db.collection("users").document(uid);
        ApiFuture<DocumentSnapshot> future = docRef.get();
        DocumentSnapshot document = future.get();

        if (document.exists()) {
            User user = document.toObject(User.class);
            ArrayList<Services> services = user.getServices();
            return services;
        }
        return null;
    }

    /**
     * Set Services
     * @param services
     * set new services
     */
    public void setServices(ArrayList<Services> services) {
        this.services = services;
    }

    /**
     * Set Widget
     * @param widgets
     * set new widgets
     */
    public void setWidgets(ArrayList<Widgets> widgets) {
        this.widgets = widgets;
    }


    /**
     * Create Services
     * @param uid
     * uid of the user
     * @param RequestToken
     * request token for API
     * @param RequestTokenSecret
     * request token secret for the API
     * @param serviceName
     * service name to create
     * @throws ExecutionException
     * if the connection is interrupted
     * @throws InterruptedException
     * if the connection is interrupted
     */
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

    /**
     * Set getUID
     * @return
     * the uid of the user
     */
    public String getUid() {
        return uid;
    }

    /**
     * Get DisplayName
     * @return
     * the name of the user
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * Get Email
     * @return
     * the mail
     */
    public String getEmail() {
        return email;
    }

    /**
     * Get Email Verified
     * @return
     * the mail verified or not
     */
    public Boolean getEmailVerified() {
        return emailVerified;
    }

    /**
     * Get Photo Url
     * @return
     * the photo url
     */
    public String getPhotoUrl() {
        return photoUrl;
    }

    /**
     * Get Services
     * @return
     * the list of all services connected
     */
    public ArrayList<Services> getServices() {
        return services;
    }

    /**
     * Get Widgets
     * @return
     * the list of widgets
     */
    public ArrayList<Widgets> getWidgets() {
        return widgets;
    }

    /**
     * To set uid of the user
     * @param uid
     * uid to set
     */
    public void setUid(String uid) {
        this.uid = uid;
    }

    /**
     * toString Function
     * @return
     * string with all data to print out
     */
    @Override
    public String toString() {
        return "User{" +
                "uid='" + uid + '\'' +
                ", displayName='" + displayName + '\'' +
                ", email='" + email + '\'' +
                ", emailVerified=" + emailVerified +
                ", photoUrl='" + photoUrl + '\'' +
                ", services=" + services +
                ", widgets=" + widgets +
                '}';
    }
}
