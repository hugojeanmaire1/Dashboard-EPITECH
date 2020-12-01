package App.controller;

import App.Model.Services;
import App.Model.User;
import com.github.scribejava.apis.GitHubApi;
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
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.view.RedirectView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Random;

/**
 * Github Controller for Github Api CAll
 */
@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/services/github")
public class GithubController {

    /**
     * Firestore to save data in db
     */
    private static final Firestore db = FirestoreClient.getFirestore();

    /**
     * OAuth service wrapper with clientID/ client-secret
     */
    final OAuth20Service service = new ServiceBuilder(System.getenv("GITHUB_CLIENTID"))
            .apiSecret(System.getenv("GITHUB_CLIENT_SECRET"))
            .defaultScope("notifications")
            .callback(System.getenv("GITHUB_CALLBACK_URL"))
            .build(GitHubApi.instance());

    /**
     * Login callback to the github platform
     * @param body
     * body of the user to send
     * @param code
     * code given by the APi
     * @param error
     * error for error handling
     * @param state
     * state given by the API
     * @return
     * a new User with all the data
     */
    @PostMapping(value="/login/callback")
    public User loginGithub(@RequestBody User body, @RequestParam(required = false) String code, @RequestParam(required = false) String error,
                                      @RequestParam(required = false) String state)
    {
        try {
            OAuth2AccessToken accessToken = service.getAccessToken(code);

            DocumentReference docRef = db.collection("users").document(body.getUid());
            ApiFuture<DocumentSnapshot> future = docRef.get();
            DocumentSnapshot document = future.get();

            if (document.exists()) {
                User user = document.toObject(User.class);
                for (Services service : user.getServices()) {
                    if (service.getName().equals("github")) {
                        try {
                            service.setAccessToken(accessToken.getAccessToken());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
                System.out.println(user);
                db.collection("users").document(body.getUid()).set(user);
                return user;
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Login function
     * @param uid
     * uid of the user to connect
     * @return
     * a view to the dashboard
     */
    @GetMapping(path = "/login")
    public RedirectView loginGithub(@RequestParam(value="uid") String uid)
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

    /**
     * Get all the repository by a name
     * @param body
     * body of the connected user with all the data
     * @param repo
     * repository to find out
     * @param res
     * response to give back to the request
     */
    @PostMapping(path = "/repositories")
    public void getOrganizations(@RequestBody User body, @RequestParam String repo, HttpServletResponse res)
    {
        String accessToken = null;
        try {
            if (body.getServices() == null)
                throw new Exception("Errow with the service");
            for (Services services: body.getServices()) {
                if (services.getName().equals("github"))
                    accessToken = services.getAccessToken();
            }
            final OAuthRequest request = new OAuthRequest(Verb.GET, "https://api.github.com/search/repositories?q=" + repo);
            service.signRequest(accessToken, request);
            Response response = service.execute(request);
            res.setContentType("application/json");
            PrintWriter out = res.getWriter();
            out.print(response.getBody());
            out.flush();
            out.close();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "You're not yet connected");
        }
    }

    /**
     * Get topics by a given name
     * @param body
     * body of the connected user
     * @param topics
     * topics to find out
     * @param res
     * response to send back to the front
     */
    @PostMapping(path = "/topics")
    public void getTopics(@RequestBody User body, @RequestParam String topics, HttpServletResponse res)
    {
        String accessToken = null;
        try {
            if (body.getServices() == null)
                throw new Exception("Errow with the service");
            for (Services services: body.getServices()) {
                if (services.getName().equals("github"))
                    accessToken = services.getAccessToken();
            }
            final OAuthRequest request = new OAuthRequest(Verb.GET, "https://api.github.com/search/topics?q=" + topics);
            request.addHeader("Accept", "application/vnd.github.mercy-preview+json");
            service.signRequest(accessToken, request);
            Response response = service.execute(request);
            res.setContentType("application/json");
            PrintWriter out = res.getWriter();
            out.print(response.getBody());
            out.flush();
            out.close();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "You're not yet connected");
        }
    }
}
