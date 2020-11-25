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

    //private String id;
    private String title;
    private String name;
    private String description;
    private HashMap<String, Object> position;
    private ArrayList<HashMap<String, String>> params;

    @JsonIgnore
    private static final Firestore db = FirestoreClient.getFirestore();

    public Widgets() {

    }

    public Widgets(String name, String description) {
        this.name = name;
        this.title = null;
        this.description = description;
        this.params = new ArrayList<>();
        this.position = new HashMap<>();
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

//    public void generateUid() {
//        this.setUid(UUID.randomUUID().toString().replace("-", ""));
//    }

    public String getName() {
        return name;
    }

    public HashMap<String, Object> getPosition() { return position; }

    public void setPosition(HashMap<String, Object> position) { this.position = position; }

    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }

    public ArrayList<HashMap<String, String>> getParams() {
        return params;
    }

    public void setParams(ArrayList<HashMap<String, String>> params) {
        this.params = params;
    }

    public String getTitle() { return title; }

    public void setTitle(String title) { this.title = title; }

}
