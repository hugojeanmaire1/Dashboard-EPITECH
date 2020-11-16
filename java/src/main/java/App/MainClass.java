package App;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.jetbrains.annotations.NotNull;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.IOException;
import java.io.InputStream;


/*
        PUSH NEW DATA TO SERVER and services
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("server/");
        DatabaseReference usersRef = ref.child("services");
        Map<String, String> users = new HashMap<>();
        users.put("alanisawesome", "Test1");
        users.put("gracehop", "Test2");
        usersRef.setValueAsync(users);
 */

/**
 * App.MainClass in order to laucnh Spring boot app
 */
@SpringBootApplication(scanBasePackages={
        "App.controller", "App.firebase", "App.Model"})
@ComponentScan({"App.controller"})
public class MainClass {

    /**
     * main in orde to launch app
     * @param args
     * args are parameters passed to the application
     */
    public static void main(String[] args){
        System.setProperty("java.net.preferIPv4Stack" , "true");

        InputStream fis = MainClass.class.getClassLoader().getResourceAsStream("ServiceAccountKey.json");
        if (fis != null) {
            try {
                FirebaseOptions firebaseOptions = new FirebaseOptions.Builder()
                        .setCredentials(GoogleCredentials.fromStream(fis))
                        .setDatabaseUrl("https://dashboard-eb9b3.firebaseio.com")
                        .build();
                FirebaseApp.initializeApp(firebaseOptions);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Error when reading the file");
        }

/*        try {
            TwitterClient twitterClient = DAO.getTwitterClientInstance();
        } catch (IOException e) {
            e.printStackTrace();
        }*/
       /* System.out.println("Construct Firebase");
        InputStream fis = InitFirebase.class.getClassLoader().getResourceAsStream("ServiceAccountKey.json");
        if (fis != null) {
            try {
                FirebaseOptions firebaseOptions = new FirebaseOptions.Builder()
                        .setCredentials(GoogleCredentials.fromStream(fis))
                        .setDatabaseUrl("https://dashboard-eb9b3.firebaseio.com")
                        .build();
                FirebaseApp.initializeApp(firebaseOptions);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Error when reading the file");
        }
        Firestore db = FirestoreClient.getFirestore();
        DocumentReference doc = db.collection("services").document("UZEPOFe9G40UhwWYVZVY");
        Map<String, Object> data = new HashMap<>();
        data.put("name", "Facebook");
        ApiFuture<WriteResult> result = doc.set(data);
        System.out.println("Update time : " + result.get().getUpdateTime());

        ApiFuture<QuerySnapshot> query = db.collection("users").get();
        QuerySnapshot querySnapshot = query.get();
        List<QueryDocumentSnapshot> documents = querySnapshot.getDocuments();
        for (QueryDocumentSnapshot document : documents) {
            if (doc.getId().equals("UZEPOFe9G40UhwWYVZVY")) {
                ApiFuture<DocumentSnapshot> q = db.collection("users").document(doc.getId()).get();
                DocumentSnapshot qs = q.get();

                System.out.println("ID: " + qs.getData());
            }
        }*/
       /* usersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot data: dataSnapshot.getChildren()) {
                    Services services = data.getValue(Services.class);
                    System.out.println("Services: " + services);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
        DatabaseReference postsRef = ref.child("services");
        ArrayList<Services> arrayList = new ArrayList<>();
        arrayList.add(new Services("Facebook"));
        arrayList.add(new Services("Twitter"));

        postsRef.setValueAsync(arrayList);*/
        /*        ref = database.getReference("users/");
        usersRef = ref.child("widgets");
        Map<String, String> widgets = new HashMap<>();
        widgets.put("Facebook", "zzzzz");
        widgets.put("Twitter", "zzzzzeezez");
        widgets.put("Instagram", "zzzezezazz");
        usersRef.setValueAsync(widgets);*/

        SpringApplication.run(MainClass.class, args);
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(@NotNull CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("http://localhost:4200")
                        .allowedMethods("GET", "PUT", "POST", "DELETE")
                        .allowedHeaders("Content-Type", "X-Requested-With", "accept", "Origin", "Access-Control-Request-Method", "Access-Control-Request-Headers")
                        .exposedHeaders("Access-Control-Allow-Origin", "Access-Control-Allow-Credentials")
                        .allowCredentials(true).maxAge(3600);
            }
        };
    }

}
