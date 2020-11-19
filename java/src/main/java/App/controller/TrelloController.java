package App.controller;

import com.sola.instagram.auth.InstagramAuthentication;
import com.wrapper.spotify.SpotifyHttpManager;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;
import java.net.URI;
import com.sola.instagram.InstagramSession;
import com.sola.instagram.auth.AccessToken;
import com.sola.instagram.exception.InstagramException;
import com.sola.instagram.io.PostMethod;
import com.sola.instagram.io.UriFactory;
import com.sola.instagram.model.User;
import com.sola.instagram.util.UriConstructor;

@RestController
@RequestMapping("/services/instagram")

public class InstagramController {

    private static final String clientId = "3988674807829305";
    private static final String clientSecretID = "88c3e3aa6a7f5efa8f795eb037539beb";
    private static final String redirectUri = "http://localhost:8080/services/instagram/login/callback";


    @RequestMapping(path="/login")
    public static Object getAuthorization() throws InstagramException {
        InstagramAuthentication auth = new InstagramAuthentication();
        String authUrl = auth.setRedirectUri(redirectUri)
                    .setClientSecret(clientSecretID)
                    .setClientId(clientId)
                    .setScope("comment+like")
                    .getAuthorizationUri();
        return new RedirectView(authUrl);
    }
}