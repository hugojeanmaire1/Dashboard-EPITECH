package App.controller;

import App.Model.Services;
import App.Model.User;
import com.github.scribejava.apis.GitHubApi;
import com.github.scribejava.apis.LinkedInApi20;
import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.model.OAuth2AccessToken;
import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Response;
import com.github.scribejava.core.model.Verb;
import com.github.scribejava.core.oauth.OAuth20Service;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;
import com.google.gson.Gson;
import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.model_objects.credentials.AuthorizationCodeCredentials;
import com.wrapper.spotify.requests.authorization.authorization_code.AuthorizationCodeRequest;
import org.apache.hc.core5.http.ParseException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.ExecutionException;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/services/github")
public class GithubController {

    private static final Firestore db = FirestoreClient.getFirestore();
    final OAuth20Service service = new ServiceBuilder("3ef842eaee82559be97c")
            .apiSecret("cdc46a4beb24472144d4500981a3d3b52397ab87")
            .defaultScope("repo:status read:org user")
            .callback("http://localhost:4200/login/github/callback")
            .build(GitHubApi.instance());

    @PostMapping(value="/login/callback")
    public User loginLinkedinCallback(@RequestBody User body, @RequestParam(required = false) String code, @RequestParam(required = false) String error,
                                      @RequestParam(required = false) String state, HttpServletResponse res)
    {
        try {
            OAuth2AccessToken accessToken = service.getAccessToken(code);

            if (body.getServices() != null) {
                for (Services services: body.getServices()) {
                    if (services.getName().equals("github")) {
                        try {
                            services.setAccessToken(accessToken.getAccessToken());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    return body;
                }
            } else {

                DocumentReference docRef = db.collection("users").document(body.getUid());
                ApiFuture<DocumentSnapshot> future = docRef.get();
                DocumentSnapshot document = future.get();

                if (document.exists()) {
                    User user = document.toObject(User.class);
                    for (Services service: user.getServices()) {
                        if (service.getName().equals("github"))
                            service.setAccessToken(accessToken.getAccessToken());
                    }
                    return user;
                }
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return body;
    }

    @GetMapping(path = "/login")
    public RedirectView loginGithub(HttpServletRequest request, @RequestParam(value="uid") String uid)
    {

        String secretState = "secret" + new Random().nextInt(999_999);
        String authorizationUrl = service.getAuthorizationUrl(secretState);
        User user = new User();
        try {
            user.createService(uid, null, null,"github");
        } catch (Exception e) {
            e.printStackTrace();
            return new RedirectView("http://localhost:4200/dashboard");

        }
        return new RedirectView(authorizationUrl);
    }

    @PostMapping(path = "/org")
    public void getProjectOrg(@RequestBody User body, @RequestParam String organization)
    {
        String accessToken = null;
        for (Services services: body.getServices()) {
            if (services.getName().equals("github"))
                accessToken = services.getAccessToken();
        }
        System.out.println(accessToken);
    }

    @PostMapping(path = "/organizations")
    public void getOrganizations(@RequestBody User body)
    {
        String accessToken = null;
        for (Services services: body.getServices()) {
            if (services.getName().equals("github"))
                accessToken = services.getAccessToken();
        }
        System.out.println(accessToken);
        final OAuthRequest request = new OAuthRequest(Verb.GET, "https://api.github.com/user/orgs");
        service.signRequest(accessToken, request);
        try (Response response = service.execute(request)) {
            System.out.println(response.getBody());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
