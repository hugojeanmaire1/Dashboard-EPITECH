package App.Model;

import lombok.*;

@Getter
@Setter
public class Twitch {

    private String clientID;
    private String clientSecret;
    private String redirectUrl;
    private CredentialsTwitch credentials;

    public Twitch(String clientID, String clientSecret, String redirectUrl) {
        this.clientID = clientID;
        this.clientSecret = clientSecret;
        this.redirectUrl = redirectUrl;
        this.credentials = null;
    }

}
