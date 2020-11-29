import App.Model.User;
import App.Model.Widgets;
import App.controller.UserController;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.awaitility.Awaitility;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.mock.web.MockHttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;
import static org.junit.Assert.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserControllerRouteTest {

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

    @DisplayName("Test Create new User")
    @Test
    public void TestCreateUser()
    {
        UserController controller = new UserController();
        MockHttpServletResponse response = new MockHttpServletResponse();
        User user = new User();
        user.setUid("abcde");
        try {
            controller.PostNewUser(user, response);
            TimeUnit.SECONDS.sleep(5);
            assertEquals(200, response.getStatus());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @DisplayName("Test user Login in")
    @Test
    public void TestLoginIn()
    {
        UserController controller = new UserController();
        MockHttpServletResponse response = new MockHttpServletResponse();
        User user = new User();
        user.setUid("abcde");
        try {
            controller.checkUser(user, response);
            TimeUnit.SECONDS.sleep(5);
            assertEquals(200, response.getStatus());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @DisplayName("Test user Login in not exist")
    @Test
    public void TestNotExisitingUser()
    {
        UserController controller = new UserController();
        MockHttpServletResponse response = new MockHttpServletResponse();
        User user = new User();
        user.setUid("z");
        try {
            controller.checkUser(user, response);
            TimeUnit.SECONDS.sleep(5);
            assertEquals(200, response.getStatus());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @DisplayName("Test user Login in not exist")
    @Test
    public void TestCreateService()
    {
        User user = new User();
        user.setUid("z");
        try {
            user.createService("z", null, null, "github");
            TimeUnit.SECONDS.sleep(5);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @DisplayName("Test Create widgets")
    @Test
    public void TestAddWidgets()
    {
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
        position.put("id", "zezeze");
        position.put("x", 0);
        position.put("y", 0);
        widgets1.setPosition(position);
        widgets1.setTitle("FindCrypto");
        try {
            UserController controller = new UserController();
            User user = controller.postWidget(widgets1, "z", "github");
            TimeUnit.SECONDS.sleep(5);
            widgets1.setName("GetRepository");
            controller.updateWidget(widgets1, "z");
            TimeUnit.SECONDS.sleep(5);
            assertEquals(controller.getWidgets("z").size(), 1);
            TimeUnit.SECONDS.sleep(5);
            controller.postWidgets(widgets1, "z");
            TimeUnit.SECONDS.sleep(5);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @DisplayName("Test Get Widgets")
    @Test
    public void TestGetWidgets()
    {
        User user = new User();
        assertEquals("User{uid='null', displayName='null', email='null', emailVerified=null, photoUrl='null', services=null, widgets=null}", user.toString());
        assertNull(user.getWidgets());
    }


}
