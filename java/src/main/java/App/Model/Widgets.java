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

    /**
     * Widgets
     */

    public Widgets() {

    }

    /**
     * Widgets
     * @param name
     * @param description
     */

    public Widgets(String name, String description) {
        this.name = name;
        this.title = null;
        this.description = description;
        this.params = new ArrayList<>();
        this.position = new HashMap<>();
    }

    /**
     * Update Position
     * @param uid
     * @param serviceName
     * @param widget
     * @throws ExecutionException
     * @throws InterruptedException
     */

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

    /**
     * Get Name
     * @return
     */

    public String getName() {
        return name;
    }

    /**
     * Get Position
     * @return
     */

    public HashMap<String, Object> getPosition() { return position; }

    /**
     * Set Position
     * @param position
     */

    public void setPosition(HashMap<String, Object> position) { this.position = position; }

    /**
     * Set Name
     * @param name
     */

    public void setName(String name) { this.name = name; }

    /**
     * Get Description
     * @return
     */

    public String getDescription() { return description; }

    /**
     * Set Description
     * @param description
     */

    public void setDescription(String description) { this.description = description; }

    /**
     * Get Params
     * @return
     */

    public ArrayList<HashMap<String, String>> getParams() {
        return params;
    }

    /**
     * Set Params
     * @param params
     */

    public void setParams(ArrayList<HashMap<String, String>> params) {
        this.params = params;
    }

    /**
     * GetT Title
     * @return
     */

    public String getTitle() { return title; }

    /**
     * Set Title
     * @param title
     */

    public void setTitle(String title) { this.title = title; }

    /**
     * toString Function
     * @return
     */

    @Override
    public String toString() {
        return "Widgets{" +
                "title='" + title + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", position=" + position +
                ", params=" + params +
                '}';
    }
}
