package App.controller;

import App.Model.Services;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * List of services
 */
@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/services")
public class ServiceListController {

    /**
     * Connection to DB
     */
    @JsonIgnore
    private static final Firestore db = FirestoreClient.getFirestore();

    /**
     * List all the services in the db
     * @return list of data
     * @throws ExecutionException
     * If the connection is interrupted
     * @throws InterruptedException
     * If the connection is interrupted
     */
    @GetMapping("/get")
    public ArrayList<Services> getServices() throws ExecutionException, InterruptedException {
        ArrayList<Services> services = new ArrayList<>();
        ApiFuture<QuerySnapshot> future = db.collection("services").get();
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();
        for (QueryDocumentSnapshot document: documents) {
            services.add(document.toObject(Services.class));
        }
        return services;
    }

}
