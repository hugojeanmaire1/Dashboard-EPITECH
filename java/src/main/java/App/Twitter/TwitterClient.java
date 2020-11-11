package App.Twitter;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TwitterClient {

    public static TwitterCredentials TWITTER_CREDENTIALS;
    public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public TwitterClient(TwitterCredentials credentials) {
        TWITTER_CREDENTIALS = credentials;
    }
}
