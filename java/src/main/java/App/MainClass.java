package App;

import App.Model.Services;
import App.Model.Widgets;
import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.*;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * App.MainClass in order to laucnh Spring boot app
 */
@SpringBootApplication(scanBasePackages={
        "App.controller", "App.Model"})
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
/*        Firestore db = FirestoreClient.getFirestore();
        Services services = new Services("coinmarketcap");
        ArrayList<Widgets> widgets = new ArrayList<>();
        Widgets widgets1 = new Widgets("FindCrypto", "Find Crypto information");
        ArrayList<HashMap<String, String>> params = new ArrayList<>();
        HashMap<String, String> map = new HashMap<>();
        map.put("name", "coins");
        map.put("type", "string");
        params.add(map);
        widgets1.setParams(params);

        HashMap<String, Object> position = new HashMap<>();
        position.put("cols", 2);
        position.put("rows", 4);
        position.put("x", 0);
        position.put("y", 0);
        widgets1.setPosition(position);
        widgets1.setTitle("FindCrypto");*/


/*        Widgets widgets2 = new Widgets("TopicList", "See Topic by Id");
        ArrayList<HashMap<String, String>> params2 = new ArrayList<>();
        HashMap<String, String> map1 = new HashMap<>();
        map1.put("name", "topics");
        map1.put("type", "string");
        params2.add(map1);
        widgets2.setParams(params2);

        HashMap<String, Object> position1 = new HashMap<>();
        position1.put("cols", 2);
        position1.put("rows", 4);
        position1.put("x", 0);
        position1.put("y", 0);
        widgets2.setPosition(position1);
        widgets2.setTitle("TopicList");*/

        /*widgets.add(widgets1);*/
        /*widgets.add(widgets2);*/

        /*services.setWidgets(widgets);*/
        /*ApiFuture<DocumentReference> future = db.collection("services").add(services);*/

        SpringApplication.run(MainClass.class, args);
    }
}
