package App.controller;

import com.wrapper.spotify.model_objects.credentials.AuthorizationCodeCredentials;
import com.wrapper.spotify.requests.authorization.authorization_code.AuthorizationCodeRequest;
import com.wrapper.spotify.requests.data.search.simplified.SearchAlbumsRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;
import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.SpotifyHttpManager;
import com.wrapper.spotify.requests.authorization.authorization_code.AuthorizationCodeUriRequest;
import java.net.URI;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/services/spotify")
public class SpotifyController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TwitterController.class);

    @RequestMapping(path="/login/callback")
    public void CallbackLogin(@RequestParam String code,
            HttpServletRequest request, HttpServletResponse response, Model model)
    {
        try {
            SpotifyApi spotifyApi = (SpotifyApi)request.getSession().getAttribute("spotifyApi");
            AuthorizationCodeRequest authorizationCodeRequest = spotifyApi.authorizationCode(code).build();
            AuthorizationCodeCredentials authorizationCodeCredentials = authorizationCodeRequest.execute();
            spotifyApi.setAccessToken(authorizationCodeCredentials.getAccessToken());
            spotifyApi.setRefreshToken(authorizationCodeCredentials.getRefreshToken());
            request.getSession().setAttribute("spotifyApi", spotifyApi);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @GetMapping(path="/login")
    public RedirectView loginSpotify(HttpServletRequest request)
    {
        URI redirectUri = SpotifyHttpManager.makeUri("http://localhost:8080/services/spotify/login/callback");
        SpotifyApi spotifyApi = new SpotifyApi.Builder()
                .setClientId("7836b9f51111454685e0c44568bf67da")
                .setClientSecret("97bd2040312b4dd499fe27d0df6cfe97")
                .setRedirectUri(redirectUri)
                .build();
        AuthorizationCodeUriRequest authorizationCodeUriRequest = spotifyApi.authorizationCodeUri()
                .show_dialog(true)
                .build();
        request.getSession().setAttribute("spotifyApi", spotifyApi);
        return new RedirectView(String.valueOf(authorizationCodeUriRequest.execute()));
    }
}
