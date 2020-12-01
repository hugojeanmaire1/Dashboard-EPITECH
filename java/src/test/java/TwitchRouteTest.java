import App.controller.TwitchController;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.junit.jupiter.api.BeforeAll;

import java.io.IOException;
import java.io.InputStream;
import App.Model.User;
import App.Model.Widgets;
import App.controller.UserController;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.web.servlet.view.RedirectView;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;
import static org.junit.Assert.*;


public class TwitchRouteTest {

    @BeforeAll
    static void BeforeTest()
    {
        InputStream fis = UserControllerRouteTest.class.getClassLoader().getResourceAsStream("ServiceAccountKey.json");
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
    public void TestLogin()
    {
        MockHttpServletRequest request = new MockHttpServletRequest();
        TwitchController controller = new TwitchController();
        try {
            TimeUnit.SECONDS.sleep(5);
            RedirectView view = controller.loginTwitch(request, "z");
            TimeUnit.SECONDS.sleep(5);
            System.err.println(view.getUrl());
            assertEquals(view.getUrl(), "");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void TestLoginCallback()
    {
        User user = new User();
        user.setUid("zd");
        TwitchController controller = new TwitchController();
        user = (User)controller.callbackTwitch(user, "abcde");
        System.err.println(user);
    }
}
