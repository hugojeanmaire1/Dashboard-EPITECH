import App.Model.Widgets;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.Assert.*;

@TestInstance(TestInstance.Lifecycle.PER_METHOD)
public class WidgetsDbTest {

    @BeforeAll
    static void BeforeTest()
    {
        InputStream fis = WidgetsDbTest.class.getClassLoader().getResourceAsStream("ServiceAccountKey.json");
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
    public void TestWidgetsDB()
    {
        Widgets widgets = new Widgets();
        assertNull(widgets.getParams());
        assertNull(widgets.getDescription());
        assertNull(widgets.getName());
        assertNull(widgets.getPosition());
        assertNull(widgets.getTitle());
        widgets = new Widgets("github", "tada");
        assertEquals(widgets.getParams().size(), 0);
        assertEquals(widgets.getDescription(), "tada");
        assertEquals(widgets.getName(), "github");
        assertEquals(widgets.getPosition().size(), 0);
        assertNull(widgets.getTitle());
        HashMap<String, Object> map = new HashMap<>();
        map.put("x", "1");
        map.put("y", "2");
        widgets.setPosition(map);
        widgets.setName("githubb");
        widgets.setDescription("toudoum");
        widgets.setTitle("netflix");
        ArrayList<HashMap<String, String>> list = new ArrayList<>();
        HashMap<String, String> str = new HashMap<>();
        str.put("params", "repo");
        str.put("type", "string");
        list.add(str);
        widgets.setParams(list);
        assertEquals(widgets.getParams().size(), 1);
        assertEquals(widgets.getDescription(), "toudoum");
        assertEquals(widgets.getName(), "githubb");
        assertEquals(widgets.getTitle(), "netflix");
        assertEquals(widgets.toString(), "Widgets{title='netflix', name='githubb', description='toudoum', position={x=1, y=2}, params=[{params=repo, type=string}]}");
    }
}
