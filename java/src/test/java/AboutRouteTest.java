import App.Model.About;
import App.controller.AboutController;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import java.io.IOException;
import java.io.InputStream;

import static org.junit.Assert.*;


public class AboutRouteTest {

    @BeforeAll
    static void BeforeTest()
    {
        InputStream fis = AboutRouteTest.class.getClassLoader().getResourceAsStream("ServiceAccountKey.json");
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

    @Test
    public void TestRouteAbout()
    {
        MockHttpServletRequest request = new MockHttpServletRequest();
        AboutController controller = new AboutController();
        About about = controller.getAbout(request);
        assertNotNull(about);
        assertNotNull(about.getServer());
        assertNotNull(about.getCustomer());
    }
}
