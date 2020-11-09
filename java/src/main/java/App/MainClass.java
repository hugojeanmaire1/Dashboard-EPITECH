package App;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/*
        InputStream fis = App.MainClass.class.getClassLoader().getResourceAsStream("ServiceAccountKey.json");
        if (fis == null) {
        FirebaseOptions optonsFirebase = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(fis))
                    .setDatabaseUrl("https://dashboard-epitech-a82c3.firebaseio.com")
                    .build();
            FirebaseApp.initializeApp(optonsFirebase);
 */

/**
 * App.MainClass in order to laucnh Spring boot app
 */
@SpringBootApplication
@ComponentScan({"App.controller"})
public class MainClass {

    /**
     * main in orde to launch app
     * @param args
     * args are parameters passed to the application
     */
    public static void main(String[] args) {
        System.setProperty("java.net.preferIPv4Stack" , "true");
        SpringApplication.run(MainClass.class, args);
    }

}
