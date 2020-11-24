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

    public Server() {
        super();
        this.current_time = System.currentTimeMillis();
        this.services = new ArrayList<ServicesAbout>();
        this.updateServices();
    }

    public long getCurrent_time() {
        return current_time;
    }

    public void setCurrent_time(long current_time) {
        this.current_time = current_time;
    }

    public ArrayList<ServicesAbout> getServices() {
        return services;
    }

    public void setServices(ArrayList<ServicesAbout> services) {
        this.services = services;
    }

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
