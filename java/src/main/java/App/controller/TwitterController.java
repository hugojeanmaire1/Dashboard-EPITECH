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
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;
import twitter4j.*;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import org.slf4j.Logger;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;


@RestController
@RequestMapping("/services/twitter")
public class TwitterController extends HttpServlet {

    private static final Logger LOGGER = LoggerFactory.getLogger(TwitterController.class);
    @JsonIgnore
    private static final Firestore db = FirestoreClient.getFirestore();
    private String consumerKey = "S2O303TWOCfZHx3e8Jt16AgCr";
    private String consumerSecret = "VEquyDohQ2YaXueBqmL0akbAJR16v0GxxTXpxRGDCjuJ9F0QRk";

    ConfigurationBuilder builder = new ConfigurationBuilder().setOAuthConsumerKey("S2O303TWOCfZHx3e8Jt16AgCr").setOAuthConsumerSecret("VEquyDohQ2YaXueBqmL0akbAJR16v0GxxTXpxRGDCjuJ9F0QRk");
//            builder.setOAuthConsumerKey("S2O303TWOCfZHx3e8Jt16AgCr");
//            builder.setOAuthConsumerSecret("VEquyDohQ2YaXueBqmL0akbAJR16v0GxxTXpxRGDCjuJ9F0QRk");
    Configuration configuration = builder.build();
    TwitterFactory factory = new TwitterFactory(configuration);


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

        String consumerKey = "S2O303TWOCfZHx3e8Jt16AgCr";
        String consumerSecret = "VEquyDohQ2YaXueBqmL0akbAJR16v0GxxTXpxRGDCjuJ9F0QRk";

        String refreshToken = null;
        String refreshTokenSecret = null;

        ConfigurationBuilder builder = new ConfigurationBuilder();
        builder.setOAuthConsumerKey(consumerKey);
        builder.setOAuthConsumerSecret(consumerSecret);
        Configuration configuration = builder.build();

        TwitterFactory factory = new TwitterFactory(configuration);
        Twitter twitter = factory.getInstance();

        DocumentReference docRef = db.collection("users").document(body.getUid());
        ApiFuture<DocumentSnapshot> future = docRef.get();
        DocumentSnapshot document = future.get();

        if (document.exists()) {
            User user = document.toObject(User.class);
            for (Services service: user.getServices()) {
                if (service.getName().equals("twitter")) {
                    refreshToken = service.getRequestToken();
                    refreshTokenSecret = service.getRequestTokenSecret();
                }
                try {
                    RequestToken requestToken = new RequestToken(refreshToken, refreshTokenSecret);
                    AccessToken accessToken = twitter.getOAuthAccessToken(requestToken, oauthVerifier);
                    System.out.println("AccessToken = " + accessToken);
                    service.setUserId(Long.toString(accessToken.getUserId()));
                    service.setUserName(accessToken.getScreenName());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            System.out.println(user);
            return user;

        }

        return null;

        //HttpSession session = request.getSession();
        //Twitter twitter = (Twitter) session.getAttribute("twitter");

        //System.out.println(request.getSession());
        //Twitter twitter = (Twitter) request.getSession().getAttribute("twitter");
        //System.out.println("Twitter = " + twitter);
        //RequestToken requestToken = (RequestToken) request.getSession().getAttribute("requestToken");
        //try {
//            if (twitter == null)
//                throw new Exception("Twitter is null");
//            AccessToken token = twitter.getOAuthAccessToken(requestToken, oauthVerifier);
//            request.getSession().removeAttribute("requestToken");
//            model.addAttribute("username", twitter.getScreenName());
//            twitter.setOAuthAccessToken(token);
            //String consumerKey = "S2O303TWOCfZHx3e8Jt16AgCr";
            //String consumerSecret = "VEquyDohQ2YaXueBqmL0akbAJR16v0GxxTXpxRGDCjuJ9F0QRk";

//            ConfigurationBuilder builder = new ConfigurationBuilder();
//            builder.setOAuthConsumerKey(consumerKey);
//            builder.setOAuthConsumerSecret(consumerSecret);
//            Configuration configuration = builder.build();

            //TwitterFactory factory = new TwitterFactory().getInstance();
            //Twitter twitter = new TwitterFactory().getInstance();

           //RequestToken requestToken = twitter.getOAuthRequestToken("http://localhost:4200/login/twitter/callback");

            //System.out.println(requestToken);
    }

    @GetMapping(path="/login")
    public void LoginTwitter(HttpServletResponse response, @RequestParam(value="uid") String uid)
    {
        String consumerKey = "S2O303TWOCfZHx3e8Jt16AgCr";
        String consumerSecret = "VEquyDohQ2YaXueBqmL0akbAJR16v0GxxTXpxRGDCjuJ9F0QRk";

        ConfigurationBuilder builder = new ConfigurationBuilder();
        builder.setOAuthConsumerKey(consumerKey);
        builder.setOAuthConsumerSecret(consumerSecret);
        Configuration configuration = builder.build();

        TwitterFactory factory = new TwitterFactory(configuration);
        Twitter twitter = factory.getInstance();

        try {
            String callbackUrl = "http://localhost:4200/login/twitter/callback";
            RequestToken requestToken = twitter.getOAuthRequestToken(callbackUrl);
            User user = new User();
            user.createService(uid, requestToken.getToken(), requestToken.getTokenSecret(),"twitter");
            response.sendRedirect(requestToken.getAuthenticationURL());
        } catch (Exception e) {
            LOGGER.error("Problem logging in with Twitter!", e);
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
}
