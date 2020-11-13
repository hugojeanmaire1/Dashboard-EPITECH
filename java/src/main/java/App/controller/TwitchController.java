package App.controller;

import com.mb3364.twitch.api.Twitch;
import com.mb3364.twitch.api.auth.Scopes;
import twitter4j.auth.AccessToken;
import java.io.IOException;
import java.net.URI;
import java.awt.Desktop;
import java.net.URISyntaxException;

public class TwitchController {

    //private static String clientId = "9j4tl8f5bq3mwtdifra5o7mvkknd8t";
    //private static final String clientSecretID = "mow96uyfwka97recdzkdqccediulbq";

    Twitch twitch = new Twitch();
    twitch.setClientId("9j4tl8f5bq3mwtdifra5o7mvkknd8t");

    URI callbackUri = new URI("http://localhost:8080/services/twitch/login/callback");
    String authUrl = twitch.auth().getAuthenticationUrl(twitch.getClientId(), callbackUri, Scopes.CHANNEL_READ);
    Desktop d = Desktop.getDesktop().browse(new URI("http://localhost:8080/services/twitch/login/callback"));

    boolean authSuccess = twitch.auth().awaitAccessToken();

    if (authSuccess) {
        String accessToken = twitch.auth().getAccessToken();
        System.out.println("AccessToken:" + accessToken);
    } else {
        System.out.println(twitch.auth().getAuthenticationError());
    }
}
