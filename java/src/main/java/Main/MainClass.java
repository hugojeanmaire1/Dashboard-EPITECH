package Main;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

import java.io.IOException;
import java.io.InputStream;

public class MainClass {

    public static void main(String[] args) throws IOException {
        System.out.println("Hello World !!!!");
        InputStream fis = MainClass.class.getClassLoader().getResourceAsStream("ServiceAccountKey.json");
        if (fis == null) {
            System.out.println("Dzezeze");
        } else {
            System.out.println("OKOKOK");
            FirebaseOptions optonsFirebase = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(fis))
                    .setDatabaseUrl("https://dashboard-epitech-a82c3.firebaseio.com")
                    .build();
            FirebaseApp.initializeApp(optonsFirebase);
        }
    }
}
