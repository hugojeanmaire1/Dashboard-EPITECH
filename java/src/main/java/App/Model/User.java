package App.Model;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;

import java.util.HashMap;
import java.util.Map;

public class User {

    public void createUser(String uid) {
        Firestore db = FirestoreClient.getFirestore();
        DocumentReference user = db.collection("users").document(uid);
        Map<String, Object> data = new HashMap<>();
        data.put("services", null);
    }
}
