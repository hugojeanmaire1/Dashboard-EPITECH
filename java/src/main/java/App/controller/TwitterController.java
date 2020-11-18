package App.controller;

import lombok.SneakyThrows;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import twitter4j.*;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import org.slf4j.Logger;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;


@Controller
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/services/twitter")
public class TwitterController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TwitterController.class);


    @GetMapping(path="/", consumes= MediaType.APPLICATION_JSON_VALUE)
    public String TwitterWelcome() {
        return "Welcome to Twitter Controller";
    }

    @RequestMapping(path="/login/callback")
    public void CallbackLogin(@RequestParam(value="oauth_verifier", required=false) String oauthVerifier,
                                @RequestParam(value="denied", required=false) String denied,
                                HttpServletRequest request, HttpServletResponse response, Model model)
    {
        if (denied != null) {
            return;
        }
        Twitter twitter = (Twitter) request.getSession().getAttribute("twitter");
        RequestToken requestToken = (RequestToken) request.getSession().getAttribute("requestToken");
        try {
            if (twitter == null)
                throw new Exception("Twitter is null");
            AccessToken token = twitter.getOAuthAccessToken(requestToken, oauthVerifier);
            request.getSession().removeAttribute("requestToken");
            model.addAttribute("username", twitter.getScreenName());
            twitter.setOAuthAccessToken(token);
            Cookie cookie = new Cookie("twitter_name", twitter.getScreenName());
            response.addCookie(cookie);
            response.sendRedirect("http://localhost:4200/dashboard");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SneakyThrows
    @GetMapping(path="/login")
    public void LoginTwitter(HttpServletRequest request, HttpServletResponse response)
    {
        String twitterUrl = "";

        try {
            String consumerKey = "S2O303TWOCfZHx3e8Jt16AgCr";
            String consumerSecret = "VEquyDohQ2YaXueBqmL0akbAJR16v0GxxTXpxRGDCjuJ9F0QRk";

            ConfigurationBuilder builder = new ConfigurationBuilder();
            builder.setOAuthConsumerKey(consumerKey);
            builder.setOAuthConsumerSecret(consumerSecret);
            Configuration configuration = builder.build();

            TwitterFactory factory = new TwitterFactory(configuration);
            Twitter twitter = factory.getInstance();
            String callbackUrl = "http://localhost:8080/services/twitter/login/callback";
            RequestToken requestToken = twitter.getOAuthRequestToken(callbackUrl);
            request.getSession().setAttribute("requestToken", requestToken);
            request.getSession().setAttribute("twitter", twitter);
            twitterUrl = requestToken.getAuthorizationURL();
        } catch (Exception e) {
            LOGGER.error("Problem logging in with Twitter!", e);
            return;
        }
        response.sendRedirect(twitterUrl);
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
