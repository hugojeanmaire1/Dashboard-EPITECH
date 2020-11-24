package App.controller;

import App.Model.Services;
import App.Model.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;
import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.model_objects.credentials.AuthorizationCodeCredentials;
import com.wrapper.spotify.requests.authorization.authorization_code.AuthorizationCodeRequest;
import com.wrapper.spotify.requests.data.search.simplified.SearchAlbumsRequest;
import org.apache.hc.core5.http.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;
import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.SpotifyHttpManager;
import com.wrapper.spotify.requests.authorization.authorization_code.AuthorizationCodeUriRequest;

import java.io.IOException;
import java.net.URI;
import java.util.concurrent.ExecutionException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/services/spotify")
public class SpotifyController {

    //private static final Logger LOGGER = LoggerFactory.getLogger(SpotifyController.class);
    @JsonIgnore
    private static final Firestore db = FirestoreClient.getFirestore();
    private static final URI redirectUri = SpotifyHttpManager.makeUri("http://localhost:4200/login/spotify/callback");
    private static final SpotifyApi spotifyApi = new SpotifyApi.Builder()
            .setClientId("1510f8bbdfae4bc2acd94094b2cac94e")
            .setClientSecret("f199f9625cd9441d93fd637602c8d146")
            .setRedirectUri(redirectUri)
            .build();

    @PostMapping(path="/login/callback")
    public User CallbackLogin(@RequestBody User body,
                                @RequestParam(value = "code")String code) throws ExecutionException, InterruptedException {
        spotifyApi.authorizationCodeUri().show_dialog(true).build();

        DocumentReference docRef = db.collection("users").document(body.getUid());
        ApiFuture<DocumentSnapshot> future = docRef.get();
        DocumentSnapshot document = future.get();

        if (document.exists()) {
            User user = document.toObject(User.class);
            for (Services service: user.getServices()) {
                if (service.getName().equals("spotify")) {
                    try {
                        AuthorizationCodeRequest authorizationCodeRequest = spotifyApi.authorizationCode(code).build();
                        AuthorizationCodeCredentials authorizationCodeCredentials = authorizationCodeRequest.execute();
                        spotifyApi.setAccessToken(authorizationCodeCredentials.getAccessToken());
                        spotifyApi.setRefreshToken(authorizationCodeCredentials.getRefreshToken());
                    } catch (ParseException | IOException | SpotifyWebApiException e) {
                        e.printStackTrace();
                    }
                }
            }
            return user;
        }
        return null;
    }

    @GetMapping(path="/login")
    public void loginSpotify(HttpServletResponse response, @RequestParam(value="uid") String uid) throws ExecutionException, InterruptedException, IOException {
        AuthorizationCodeUriRequest authorizationCodeUriRequest = spotifyApi.authorizationCodeUri()
                .show_dialog(true)
                .build();
        User user = new User();
        user.createService(uid, null, null, "spotify");
        System.out.println("LAAAAA !!!" + String.valueOf(authorizationCodeUriRequest.execute()));
        response.sendRedirect(String.valueOf(authorizationCodeUriRequest.execute()));
    }
}
