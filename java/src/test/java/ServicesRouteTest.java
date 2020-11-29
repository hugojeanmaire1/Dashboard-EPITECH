import App.Model.Services;
import App.controller.ServiceListController;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class ServicesRouteTest {

    @BeforeAll
    static void BeforeTest()
    {
        InputStream fis = ServicesRouteTest.class.getClassLoader().getResourceAsStream("ServiceAccountKey.json");
        if (fis != null) {
            try {
                FirebaseOptions firebaseOptions = new FirebaseOptions.Builder()
                        .setCredentials(GoogleCredentials.fromStream(fis))
                        .setDatabaseUrl("https://dashboard-eb9b3.firebaseio.com")
                        .build();
                if (FirebaseApp.getApps().size() != 0 && FirebaseApp.getInstance(FirebaseApp.DEFAULT_APP_NAME) != null)
                    FirebaseApp.getInstance(FirebaseApp.DEFAULT_APP_NAME).delete();
                FirebaseApp.initializeApp(firebaseOptions);

            } catch (IOException ignored) {
            }
        } else {
            System.err.println("Error when reading file");
        }
    }

    @DisplayName("Test Services Route GET")
    @Test
    public void TestServicesRouteList()
    {
        ServiceListController controller = new ServiceListController();
        try {
            ArrayList<Services> services = controller.getServices();
            assertNotNull(services);
            assertEquals(6, services.size());
        } catch (Exception ignored) {
        }
    }

}
