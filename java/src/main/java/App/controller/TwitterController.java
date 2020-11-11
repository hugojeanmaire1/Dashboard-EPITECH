package App.controller;

import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;
import twitter4j.*;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import org.slf4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@RestController
@RequestMapping("/services/twitter")
public class TwitterController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TwitterController.class);


    @GetMapping(path="/", consumes= MediaType.APPLICATION_JSON_VALUE)
    public String TwitterWelcome() {
        return "Welcome to Twitter Controller";
    }

    @RequestMapping(path="/login/callback")
    public Twitter CallbackLogin(@RequestParam(value="oauth_verifier", required=false) String oauthVerifier,
                                @RequestParam(value="denied", required=false) String denied,
                                HttpServletRequest request, HttpServletResponse response, Model model)
    {
        if (denied != null) {
            return null;
        }
        Twitter twitter = DAO.getTwitter();
        RequestToken requestToken = (RequestToken) request.getSession().getAttribute("requestToken");
        try {
            AccessToken token = twitter.getOAuthAccessToken(requestToken, oauthVerifier);

            request.getSession().removeAttribute("requestToken");

            model.addAttribute("username", twitter.getScreenName());
            twitter.setOAuthAccessToken(token);

            return twitter;
        } catch (Exception e) {
            LOGGER.error("Problem getting token!",e);
            return null;
        }
    }

    @GetMapping(path="/login")
    public RedirectView LoginTwitter(HttpServletRequest request)
    {
        String twitterUrl = "";

        try {
            Twitter twitter = DAO.getTwitter();
            String callbackUrl = "http://localhost:8080/services/twitter/login/callback";
            RequestToken requestToken = twitter.getOAuthRequestToken(callbackUrl);
            request.getSession().setAttribute("requestToken", requestToken);
            request.getSession().setAttribute("twitter", twitter);
            twitterUrl = requestToken.getAuthorizationURL();
        } catch (Exception e) {
            LOGGER.error("Problem logging in with Twitter!", e);
        }

        RedirectView redirectView = new RedirectView();
        redirectView.setUrl(twitterUrl);
        return redirectView;
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
    public Status postTweet(@PathVariable String data)
    {
        try {
            Twitter twitter = DAO.getTwitter();
            return (twitter.updateStatus(data));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
