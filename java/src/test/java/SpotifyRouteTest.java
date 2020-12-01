import App.controller.SpotifyController;
import App.controller.TwitchController;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.wrapper.spotify.model_objects.specification.AlbumSimplified;
import com.wrapper.spotify.model_objects.specification.Artist;
import com.wrapper.spotify.model_objects.specification.PlaylistSimplified;
import org.junit.jupiter.api.BeforeAll;

import java.io.IOException;
import java.io.InputStream;
import App.Model.User;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletResponse;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;


public class SpotifyRouteTest {

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
        MockHttpServletResponse response = new MockHttpServletResponse();
        SpotifyController controller = new SpotifyController();
        try {
            TimeUnit.SECONDS.sleep(5);
            controller.loginSpotify(response, "z");
            TimeUnit.SECONDS.sleep(5);
            System.err.println(response.getRedirectedUrl());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void TestLoginCallback()
    {
        User user = new User();
        user.setUid("zd");
        SpotifyController controller = new SpotifyController();
        try {
            user = controller.CallbackLogin(user, "abcde");
            System.err.println(user);
        } catch (Exception ignored) {

        }
    }

    @Test
    public void TestGetArtist()
    {
        SpotifyController controller = new SpotifyController();
        try {
            Artist[] artists = controller.getArtist("booba");
            System.err.println(Arrays.toString(artists));
        } catch (Exception ignored) {

        }
    }

    @Test
    public void TestGetAlbums()
    {
        SpotifyController controller = new SpotifyController();
        try {
            AlbumSimplified[] artists = controller.getAlbums("booba");
            System.err.println(Arrays.toString(artists));
        } catch (Exception ignored) {

        }
    }

    @Test
    public void TestGetPlaylist()
    {
        SpotifyController controller = new SpotifyController();
        try {
            PlaylistSimplified[] artists = controller.getPlaylist("RapCaviar");
            System.err.println(Arrays.toString(artists));
        } catch (Exception ignored) {

        }
    }
}
