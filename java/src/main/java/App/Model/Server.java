package App.Model;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.firebase.cloud.FirestoreClient;
import java.util.ArrayList;
import java.util.List;

/**
 * Server class to get data about server like services and widgets
 */
public class Server {

    /**
     * Current Timestamp when the request is handle
     */
    private long current_time;

    /**
     * List of all services define on the server
     */
    private ArrayList<ServicesAbout> services;

    /**
     * Server constructor
     */
    public Server() {
        super();
        this.current_time = System.currentTimeMillis();
        this.services = new ArrayList<ServicesAbout>();
        this.updateServices();
    }

    /**
     * Get Current Time
     * @return
     * the current timestamp
     */
    public long getCurrent_time() {
        return current_time;
    }

    /**
     * Set Current Time
     * @param current_time
     * set a new timestamp
     */
    public void setCurrent_time(long current_time) {
        this.current_time = current_time;
    }

    /**
     * Get the Services
     * @return
     * the list of all services
     */
    public ArrayList<ServicesAbout> getServices() {
        return services;
    }

    /**
     * Set the Services
     * @param services
     * set a new list of services
     */
    public void setServices(ArrayList<ServicesAbout> services) {
        this.services = services;
    }

    /**
     * Update the Services
     */
    private void updateServices()
    {
        ArrayList<ServicesAbout> s = new ArrayList<>();
        try {
            Firestore db = FirestoreClient.getFirestore();
            ApiFuture<QuerySnapshot> future = db.collection("services").get();
            List<QueryDocumentSnapshot> documents = future.get().getDocuments();
            for (QueryDocumentSnapshot document: documents)
                this.services.add(document.toObject(ServicesAbout.class));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
