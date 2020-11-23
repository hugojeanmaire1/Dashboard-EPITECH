package App.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;
import org.w3c.dom.stylesheets.DocumentStyle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TransferQueue;

public class Widgets {

    private String uid;
    private String title;
    private String name;
    private String description;
    private HashMap<String, String> position;
    private HashMap<String, String> params;

    @JsonIgnore
    private static final Firestore db = FirestoreClient.getFirestore();

    public Widgets() {

    }

    public Widgets(String name, String description) {
        this.name = name;
        this.description = description;
        this.params = new HashMap<String, String>();
    }

    public void updatePosition(String uid, String serviceName, Widgets widget) throws ExecutionException, InterruptedException {
        DocumentReference docRef = db.collection("users").document(uid);

        ApiFuture<DocumentSnapshot> future = docRef.get();
        DocumentSnapshot document = future.get();

        if (document.exists()) {
            User data = document.toObject(User.class);
            ArrayList<Services> services = data.getServices();
        }

    }

    public void generateUid() {
        this.setUid(UUID.randomUUID().toString().replace("-", ""));
    }

    public String getName() {
        return name;
    }

    public HashMap<String, String> getPosition() { return position; }

    public void setPosition(HashMap<String, String> position) { this.position = position; }

    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }

    public HashMap<String, String> getParams() { return params; }

    public void setParams(HashMap<String, String> params) { this.params = params; }

    public String getUid() { return uid; }

    public void setUid(String uid) { this.uid = uid; }

    public String getTitle() { return title; }

    public void setTitle(String title) { this.title = title; }

    public void printData() {
        System.out.println("Widget Data:");
        System.out.println("     " + this.getUid());
        System.out.println("     " + this.getTitle());
        System.out.println("     " + this.getName());
        System.out.println("     " + this.getDescription());
        System.out.println("     " + this.getParams());
        System.out.println("     " + this.getPosition());
        System.out.println();
    }

}
