package App.Model;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.firebase.cloud.FirestoreClient;

import java.util.ArrayList;
import java.util.List;

public class Server {

    private long current_time;
    private ArrayList<ServicesAbout> services;

    /**
     * Server
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
     */

    public long getCurrent_time() {
        return current_time;
    }

    /**
     * Set Current Time
     * @param current_time
     */

    public void setCurrent_time(long current_time) {
        this.current_time = current_time;
    }

    /**
     * Get the Services
     * @return
     */

    public ArrayList<ServicesAbout> getServices() {
        return services;
    }

    /**
     * Set the Services
     * @param services
     */

    public void setServices(ArrayList<ServicesAbout> services) {
        this.services = services;
    }

    /**
     * Update the Services
     */

    private void updateServices()
    {
        try {
            Firestore db = FirestoreClient.getFirestore();
            ApiFuture<QuerySnapshot> future = db.collection("services").get();
            List<QueryDocumentSnapshot> documents = future.get().getDocuments();
            for (QueryDocumentSnapshot document: documents)
                this.services.add(document.toObject(ServicesAbout.class));
        } catch (Exception e) {

        }
    }

}
