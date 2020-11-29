import App.controller.CoinMarketcapController;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import okhttp3.Request;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.web.servlet.view.RedirectView;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class CMCRouteTest {

    @BeforeAll
    static void BeforeTest()
    {
        InputStream fis = CMCRouteTest.class.getClassLoader().getResourceAsStream("ServiceAccountKey.json");
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

    @DisplayName("Test Get Coin CMC")
    @Test
    public void TestGetCoin()
    {
        MockHttpServletResponse response = new MockHttpServletResponse();
        CoinMarketcapController controller = new CoinMarketcapController();
        controller.getCoin("bitcoin", response);
        assertEquals(response.getStatus(), 200);
    }

    @DisplayName("Login CMC")
    @Test
    public void TestException()
    {
        MockHttpServletResponse response = new MockHttpServletResponse();
        CoinMarketcapController controller = new CoinMarketcapController();
        RedirectView view = controller.loginCMC("azerty");
        assertEquals(view.getUrl(), "http://localhost:4200/dashboard");
    }

    @DisplayName("Request Builder CMC")
    @Test
    public void TestRequestBuilder()
    {
        Map<String, String> headers = new HashMap<>();
        headers.put("Accept", "Application/json");
        CoinMarketcapController controller = new CoinMarketcapController();
        Request request = controller.getRequest("https://api.coingecko.com/api/v3/coins/", null, headers);
        assertNotNull(request);
    }
}
