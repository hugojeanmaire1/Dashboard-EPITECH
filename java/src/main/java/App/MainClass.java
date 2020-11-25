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
import java.util.List;
import java.util.concurrent.ExecutionException;

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

/*        Firestore db = FirestoreClient.getFirestore();
        Services services = new Services("Github");
        ArrayList<Widgets> widgets = new ArrayList<>();
        Widgets widgets1 = new Widgets("ProjectOrganization", "See Projects Organization");
        ArrayList<HashMap<String, String>> params = new ArrayList<>();
        HashMap<String, String> map = new HashMap<>();
        map.put("name", "organization");
        map.put("type", "string");
        params.add(map);
        widgets1.setParams(params);

        HashMap<String, Object> position = new HashMap<>();
        position.put("cols", 2);
        position.put("rows", 4);
        position.put("x", 0);
        position.put("y", 0);
        widgets1.setPosition(position);
        widgets1.setTitle("Find Project Organization");
        widgets.add(widgets1);

        services.setWidgets(widgets);
        ApiFuture<DocumentReference> future = db.collection("services").add(services);*/

        SpringApplication.run(MainClass.class, args);
    }



    /**
     * In order to configure CORS
     * @return
     * WebMVCConfigurer
     */
/*    @Bean
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
    }*/

}
