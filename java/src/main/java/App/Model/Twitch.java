package App.Model;

import lombok.*;


/**
 * Twitch Class
 */
@Getter
@Setter
public class Twitch {

    /**
     * Client ID for the API
     */
    private String clientID;

    /**
     * Client Secret
     */
    private String clientSecret;

    /**
     * Redirect URL
     */
    private String redirectUrl;

    /**
     * Credentials to handle API Call
     */
    private CredentialsTwitch credentials;

    /**
     * Twitch
     * @param clientID
     * client ID given by the API
     * @param clientSecret
     * CLient secret
     * @param redirectUrl
     * redirect URL
     */
    public Twitch(String clientID, String clientSecret, String redirectUrl) {
        this.clientID = clientID;
        this.clientSecret = clientSecret;
        this.redirectUrl = redirectUrl;
        this.credentials = null;
    }

}
