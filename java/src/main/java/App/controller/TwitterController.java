package App.controller;

import App.Twitter.TwitterClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/services/twitter")
public class TwitterController {

    @GetMapping(path="/", consumes= MediaType.APPLICATION_JSON_VALUE)
    public String TwitterWelcome() {
        return "Welcome to Twitter Controller";
    }

    @GetMapping(path="/{username}")
    public String TwitterFindUser(@PathVariable String username)
    {
        App.Twitter.TwitterClient twitterClient = DAO.getTwitterClientInstance();
        return "OKOK";
    }

    @GetMapping(path="/tweet/post/{data}")
    public String postTweet(@PathVariable String data)
    {
        TwitterClient twitterClient = DAO.getTwitterClientInstance();
        return "OKOK";
    }
}
