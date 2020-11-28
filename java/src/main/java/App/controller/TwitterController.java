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

/**
 * Twitter functions
 */
@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/services/twitter")
public class TwitterController {

    /**
     * Static variables
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(TwitterController.class);
    @JsonIgnore
    private static final Firestore db = FirestoreClient.getFirestore();
    private final Configuration configuration = new ConfigurationBuilder()
        .setOAuthConsumerKey("S2O303TWOCfZHx3e8Jt16AgCr")
        .setOAuthConsumerSecret("VEquyDohQ2YaXueBqmL0akbAJR16v0GxxTXpxRGDCjuJ9F0QRk")
        .build();
    TwitterFactory factory = new TwitterFactory(configuration);
    Twitter twitter = factory.getInstance();

    /**
     * Inutile
     * @return
     */
    @GetMapping(path="/", consumes= MediaType.APPLICATION_JSON_VALUE)
    public String TwitterWelcome() {
        return "Welcome to Twitter Controller";
    }

    /**
     * Get acccess_token and refresh_token
     * @param body body of the request
     * @param oauthVerifier id of the request
     * @param denied je sais pas
     * @return user infos form firestore
     * @throws ExecutionException
     * @throws InterruptedException
     */
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
                        service.setUserId(Long.toString(accessToken.getUserId()));
                        service.setUserName(accessToken.getScreenName());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            return user;

        }
        return null;
    }

    /**
     * Login to twitter plateform
     * @param response link redirect
     * @param uid user id
     * @throws IOException
     */
    @GetMapping(path="/login")
    public void LoginTwitter(HttpServletResponse response, @RequestParam(value="uid") String uid) throws IOException {
        try {
            String callbackUrl = "http://localhost:4200/login/twitter/callback";
            RequestToken requestToken = twitter.getOAuthRequestToken(callbackUrl);
            User user = new User();
            user.createService(uid, requestToken.getToken(), requestToken.getTokenSecret(),"twitter");
            response.sendRedirect(requestToken.getAuthenticationURL());
        } catch (IllegalStateException error) {
            System.out.println("Already Log");
            response.sendRedirect("http://localhost:4200/dashboard");
        } catch (Exception error) {
            LOGGER.error("Problem logging in with Twitter!", error);
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

    /**
     * Post a tweet
     * @param data text of the tweet
     * @param request nothing
     * @return
     */
    @GetMapping(path="/tweet/post/{data}")
    public Status postTweet(@PathVariable String data, HttpServletRequest request)
    {
        try {
            return (twitter.updateStatus(data));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Get the timeline of a tweeter user
     * @param username name of the user to follow
     * @return list of data
     * @throws TwitterException
     */
    @GetMapping(path = "/timeline")
    public List getTimeline(@RequestParam(value = "user")String username) throws TwitterException {
        return twitter.getUserTimeline(username);
    }

    /**
     * Get a list of tweet
     * @param search name
     * @return list of data
     */
    @GetMapping(path = "/search/tweet")
    public List getTweets(@RequestParam(value = "search")String search) {
        try {
            Query query = new Query(search);
            QueryResult result = twitter.search(query);
            return result.getTweets();
        } catch (TwitterException te) {
            te.printStackTrace();
            return null;
        }
    }
}
