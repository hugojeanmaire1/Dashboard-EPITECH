package App.controller;

import App.Model.Services;
import App.Model.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import twitter4j.*;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import org.slf4j.Logger;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

import java.util.*;


@RestController
@RequestMapping("/services/twitter")
public class TwitterController extends HttpServlet {

    private static final Logger LOGGER = LoggerFactory.getLogger(TwitterController.class);
    @JsonIgnore
    private static final Firestore db = FirestoreClient.getFirestore();
    private final Configuration configuration = new ConfigurationBuilder()
        .setOAuthConsumerKey("S2O303TWOCfZHx3e8Jt16AgCr")
        .setOAuthConsumerSecret("VEquyDohQ2YaXueBqmL0akbAJR16v0GxxTXpxRGDCjuJ9F0QRk")
        .build();
    TwitterFactory factory = new TwitterFactory(configuration);
    Twitter twitter = factory.getInstance();


    @GetMapping(path="/", consumes= MediaType.APPLICATION_JSON_VALUE)
    public String TwitterWelcome() {
        return "Welcome to Twitter Controller";
    }

    @PostMapping(value = "/login/callback")
    public User CallbackLogin(@RequestBody User body,
                                 @RequestParam(value="oauth_verifier", required=false) String oauthVerifier,
                                 @RequestParam(value="denied", required=false) String denied) throws ExecutionException, InterruptedException {
        if (denied != null) {
            return null;
        }
        DocumentReference docRef = db.collection("users").document(body.getUid());
        ApiFuture<DocumentSnapshot> future = docRef.get();
        DocumentSnapshot document = future.get();

        if (document.exists()) {
            User user = document.toObject(User.class);
            for (Services service: user.getServices()) {
                if (service.getName().equals("twitter")) {
                    try {
                        RequestToken requestToken = new RequestToken(service.getRequestToken(), service.getRequestTokenSecret());
                        AccessToken accessToken = twitter.getOAuthAccessToken(requestToken, oauthVerifier);
                        twitter.setOAuthAccessToken(accessToken);
                        //System.out.println("AccessToken = " + accessToken);
                        service.setUserId(Long.toString(accessToken.getUserId()));
                        service.setUserName(accessToken.getScreenName());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            System.out.println(user);
            return user;

        }
        return null;
    }

    @GetMapping(path="/login")
    public void LoginTwitter(HttpServletResponse response, @RequestParam(value="uid") String uid) throws IOException {
        try {
            String callbackUrl = "http://localhost:4200/login/twitter/callback";
            RequestToken requestToken = twitter.getOAuthRequestToken(callbackUrl);
            User user = new User();
            user.createService(uid, requestToken.getToken(), requestToken.getTokenSecret(),"twitter");
            response.sendRedirect(requestToken.getAuthenticationURL());
        } catch (Exception e) {
            LOGGER.error("Problem logging in with Twitter!", e);
            response.sendRedirect("http://localhost:4200/dashboard");
        }
    }

/*    @GetMapping(path="/{username}")
    public Status TwitterFindUser(@PathVariable String username)
    {
        try {
            Twitter twitter = DAO.getTwitter();
            return (twitter.updateStatus("Salut from api"));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }*/

    @GetMapping(path="/tweet/post/{data}")
    public Status postTweet(@PathVariable String data, HttpServletRequest request)
    {
        try {
            Twitter twitter = (Twitter) request.getSession().getAttribute("twitter");
            return (twitter.updateStatus(data));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @GetMapping(path = "/timeline")
    public List getTimeline(@RequestParam(value = "user")String username) throws TwitterException {
        return twitter.getUserTimeline(username);
    }
}
